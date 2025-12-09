package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.DroneDto
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.DroneStatus
import ru.bardinpetr.itmo.meddelivery.app.modules.transport.DroneSender
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController

@RequestMapping("/api/drone")
@RestController
class DroneController(
    private val droneSender: DroneSender
) : AbstractCommonRestController<Drone, DroneDto>(Drone::class) {

    override fun preCreateHook(e: Drone) {
        super.preCreateHook(e)
        e.status = DroneStatus.IDLE
        e.flightTask = null
    }

    @PostMapping("/{id}/send")
    fun sendDrone(@PathVariable id: Long): Boolean {
        val drone = repository
            .findById(id)
            .orElseThrow { IllegalArgumentException("Not found") }
        if (drone.status != DroneStatus.READY)
            throw IllegalArgumentException("Drone is not ready")
        droneSender.sendDrone(drone)
        return true
    }
}