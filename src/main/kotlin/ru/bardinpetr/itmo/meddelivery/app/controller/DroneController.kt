package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.DroneDto
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.Drone
import ru.bardinpetr.itmo.meddelivery.app.service.DroneService
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController
import ru.bardinpetr.itmo.meddelivery.common.utils.error.NotFoundException

@RequestMapping("/api/drone")
@RestController
class DroneController(override val service: DroneService) :
    AbstractCommonRestController<Drone, DroneDto>(Drone::class, service) {

    @PostMapping("/{id}/send")
    fun sendDrone(@PathVariable id: Long): Boolean {
        service
            .get(id)
            ?.let(service::sendDrone)
            ?: throw NotFoundException()
        return true
    }
}
