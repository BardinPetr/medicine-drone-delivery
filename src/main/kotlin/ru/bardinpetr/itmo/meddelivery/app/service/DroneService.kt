package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.DroneStatus
import ru.bardinpetr.itmo.meddelivery.app.modules.transport.DroneSender
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService

@Service
class DroneService(
    private val droneSender: DroneSender
) : AbstractBaseService<Drone>(Drone::class) {

    override fun create(entity: Drone): Drone =
        entity
            .copy(status = DroneStatus.IDLE, flightTask = null)
            .let { super.create(it) }

    /**
     * Called when drone is packed and warehouse and ready to depart
     */
    @Transactional
    fun sendDrone(drone: Drone) {
        require(drone.status == DroneStatus.READY) { "Drone is not ready" }
        droneSender.sendDrone(drone)
    }
}
