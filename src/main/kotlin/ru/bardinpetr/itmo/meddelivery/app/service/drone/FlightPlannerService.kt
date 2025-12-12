package ru.bardinpetr.itmo.meddelivery.app.service.drone

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.app.entities.FlightTask
import ru.bardinpetr.itmo.meddelivery.app.entities.Request
import ru.bardinpetr.itmo.meddelivery.app.entities.RequestEntry
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.DroneStatus
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.TaskStatus
import ru.bardinpetr.itmo.meddelivery.app.events.ExecutePlanningApplicationEvent
import ru.bardinpetr.itmo.meddelivery.app.repository.FlightTaskRepository
import ru.bardinpetr.itmo.meddelivery.app.service.RequestEntryService
import ru.bardinpetr.itmo.meddelivery.app.service.RouteService
import ru.bardinpetr.itmo.meddelivery.app.service.WarehouseService
import ru.bardinpetr.itmo.meddelivery.common.utils.logger
import java.time.Instant
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.math.min

@Service
open class FlightPlannerService(
    private val reqEntryService: RequestEntryService,
    private val warehouseService: WarehouseService,
    private val droneService: DroneService,
    private val flightTaskRepo: FlightTaskRepository,
    private val routeService: RouteService,
) : ApplicationListener<ExecutePlanningApplicationEvent> {
    @Autowired
    @Lazy
    private lateinit var _self: FlightPlannerService
    private val self
        get() = if (this::_self.isInitialized) _self else this

    private val log = logger<FlightPlannerService>()
    private val lock = ReentrantLock()

    override fun onApplicationEvent(event: ExecutePlanningApplicationEvent) {
        self.processUnfulfilledRequests()
    }

    @Transactional
    fun processUnfulfilledRequests() {
        reqEntryService
            .getUnfulfilledRequests()
            .forEach { rq, list -> assign(rq, list) }
    }

    @Transactional
    protected fun assign(request: Request, unfulfilled: List<RequestEntry>) {
        unfulfilled
            .flatMap { entry -> self.assignWarehouseAndDrone(request, entry) }
            .map { ft -> self.assignRoute(ft) }
    }

    @Transactional
    protected fun assignWarehouseAndDrone(request: Request, entry: RequestEntry): List<FlightTask> = lock.withLock {
        val tasks = mutableListOf<FlightTask>()
        val targetQuantity = entry.quantity
        var leftQuantity = targetQuantity

        val availability = warehouseService.findAvailableWarehouses(entry).toMutableList()
        val drones = droneService.findIdleDrones().toMutableList()

        val flightTaskTemplate = FlightTask(
            status = TaskStatus.QUEUED,
            request = request,
            productType = entry.productType,
            medicalFacility = request.medicalFacility
        )

        log.info("Started planning request ${request.id}: ${entry.quantity} pieces of ${entry.productType.type}")

        while (leftQuantity > 0 && availability.isNotEmpty() && drones.isNotEmpty()) {
            val nextWarehouseProduct = availability.first()
            val nextWarehouse = nextWarehouseProduct.warehouse
            val allowedToTake = min(nextWarehouseProduct.quantity, leftQuantity)

            val drone = drones.removeFirst()
            val assignedQuantity = min(drone.typeOfDrone.maxWeight.toInt(), allowedToTake)
            if (assignedQuantity < 1) break

            val task = flightTaskRepo.save(
                flightTaskTemplate.copy(
                    quantity = assignedQuantity,
                    warehouse = nextWarehouse,
                    drones = mutableListOf(drone),
                    timestamp = Instant.now(),
                )
            )
            tasks.add(task)
            droneService.updateDrone(drone.id!!) {
                location = nextWarehouse.location
                status = DroneStatus.READY
                flightTask = task
            }

            warehouseService.setProductQuantity(
                nextWarehouseProduct.product.id!!,
                nextWarehouse.id!!,
                nextWarehouseProduct.quantity - assignedQuantity
            )

            if (nextWarehouseProduct.quantity <= assignedQuantity) {
                availability.removeFirst()
            } else {
                availability.first().quantity -= assignedQuantity
            }
            leftQuantity -= assignedQuantity
            log.info("Assigned partially $assignedQuantity items from WH#${nextWarehouse.id} by DR#${drone.id}")
        }

        reqEntryService.entryIncreaseFulfilledQuantity(entry.id!!, targetQuantity - leftQuantity)

        log.info("Assigned drones: ${tasks.map { it.drones.first().id!! }.joinToString(";")}")
        return flightTaskRepo.saveAllAndFlush(tasks)
    }

    @Transactional
    protected fun assignRoute(task: FlightTask): FlightTask {
        task.route = routeService.findOrCreateRoute(task.warehouse?.id!!, task.medicalFacility?.id!!)
        task.status = TaskStatus.PACKING
        return flightTaskRepo.save(task)
    }
}
