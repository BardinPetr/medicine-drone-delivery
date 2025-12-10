package ru.bardinpetr.itmo.meddelivery.app.service.drone

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.app.entities.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.DroneStatus
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.TaskStatus
import ru.bardinpetr.itmo.meddelivery.app.repository.DroneRepository
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService

@Service
class DroneService(
    override val repo: DroneRepository,
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
    }

    fun findIdleDrones(): List<Drone> =
        repo.findAllByStatus(DroneStatus.IDLE)
            .sortedByDescending { it.typeOfDrone.maxWeight }
}
