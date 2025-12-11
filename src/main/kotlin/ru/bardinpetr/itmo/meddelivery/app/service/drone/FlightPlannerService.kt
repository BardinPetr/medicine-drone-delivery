package ru.bardinpetr.itmo.meddelivery.app.service.drone

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.app.entities.FlightTask
import ru.bardinpetr.itmo.meddelivery.app.entities.Request
import ru.bardinpetr.itmo.meddelivery.app.entities.RequestEntry
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.TaskStatus
import ru.bardinpetr.itmo.meddelivery.app.events.ExecutePlanningApplicationEvent
import ru.bardinpetr.itmo.meddelivery.app.repository.FlightTaskRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.RequestRepository
import ru.bardinpetr.itmo.meddelivery.app.service.RequestEntryService
import ru.bardinpetr.itmo.meddelivery.app.service.RouteService
import ru.bardinpetr.itmo.meddelivery.app.service.WarehouseService
import java.time.Instant
import kotlin.math.min

@Service
open class FlightPlannerService(
    private val requestRepository: RequestRepository,
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
    fun processNewRequest(request: Request) {
        assign(request, request.requestEntries)
    }

    @Transactional
    protected fun assign(request: Request, unfulfilled: List<RequestEntry>) {
        unfulfilled
            .flatMap { entry -> self.assignWarehouseAndDrone(request, entry) }
            .map { ft -> self.assignRoute(ft) }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected fun assignWarehouseAndDrone(request: Request, entry: RequestEntry): List<FlightTask> {
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

        while (leftQuantity > 0 && availability.isNotEmpty() && drones.isNotEmpty()) {
            val nextWarehouseProduct = availability.first()
            val nextWarehouse = nextWarehouseProduct.warehouse
            val allowedToTake = min(nextWarehouseProduct.quantity, leftQuantity)

            val drone = drones.removeFirst()
            val assignedQuantity = min(drone.typeOfDrone.maxWeight.toInt(), allowedToTake)
            if (assignedQuantity < 1) break

            tasks.add(
                flightTaskTemplate.copy(
                    quantity = assignedQuantity,
                    warehouse = nextWarehouse,
                    drones = mutableListOf(drone),
                    timestamp = Instant.now(),
                )
            )
            droneService.updateDrone(drone.id!!) { location = nextWarehouse.location }

            if (nextWarehouseProduct.quantity <= assignedQuantity) {
                availability.removeFirst()
            } else {
                availability.first().quantity -= assignedQuantity
            }

            warehouseService.setProductQuantity(
                nextWarehouseProduct.product.id!!,
                nextWarehouse.id!!,
                nextWarehouseProduct.quantity - assignedQuantity
            )
        }

        request.status = TaskStatus.IN_PROGRESS
        requestRepository.save(request)

        return flightTaskRepo.saveAllAndFlush(tasks)
    }

    @Transactional
    protected fun assignRoute(task: FlightTask): FlightTask {
        task.route = routeService.findOrCreateRoute(task.warehouse?.id!!, task.medicalFacility?.id!!)
        task.status = TaskStatus.PACKING
        return flightTaskRepo.save(task)
    }
}
