package ru.bardinpetr.itmo.meddelivery.app.service.drone

import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.app.entities.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.Point
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.DroneStatus
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.TaskStatus
import ru.bardinpetr.itmo.meddelivery.app.events.EventSenderService
import ru.bardinpetr.itmo.meddelivery.app.repository.DroneRepository
import ru.bardinpetr.itmo.meddelivery.app.service.fleet.IDroneFleet
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.ws.NotifyChangeType

@Service
class DroneService(
    override val repo: DroneRepository,
    private val evt: EventSenderService,
    @Lazy private val fleet: IDroneFleet
) : AbstractBaseService<Drone>(Drone::class, repo) {

    @Transactional
    override fun create(entity: Drone): Drone =
        entity
            .copy(status = DroneStatus.IDLE, flightTask = null)
            .let { super.create(it) }

    /**
     * Called when drone is packed at warehouse and ready to depart
     */
    @Transactional
    fun sendDrone(drone: Drone) {
        require(drone.status == DroneStatus.READY) { "Drone is not ready" }
        drone.location = drone.flightTask?.route?.warehouse?.location!!
        drone.status = DroneStatus.FLYING_TO
        drone.flightTask!!.status = TaskStatus.IN_PROGRESS
        repo.save(drone)
        fleet.launch(drone.flightTask!!)
    }

    fun findIdleDrones(): List<Drone> =
        repo.findAllByStatus(DroneStatus.IDLE)
            .sortedByDescending { it.typeOfDrone.maxWeight }

    @Transactional
    fun updateDrone(id: IdType, block: Drone.() -> Unit) =
        get(id)
            ?.apply(block)
            ?.let { repo.save(it) }
            ?.also { notifier.notifyChanges(clazz, id, NotifyChangeType.MOD) }

    @Transactional
    fun droneArrived(droneId: IdType) {
        updateDrone(droneId) {
            status = DroneStatus.IDLE
            flightTask = null
        }
        evt.sendProcessPlans()
    }

    @Transactional
    fun updatePosition(droneId: IdType, newPoint: Point) {
        updateDrone(droneId) { location = newPoint }
    }

    @Transactional
    fun updateDroneState(droneId: IdType, newStatus: DroneStatus) {
        updateDrone(droneId) { status = newStatus }
    }
}
