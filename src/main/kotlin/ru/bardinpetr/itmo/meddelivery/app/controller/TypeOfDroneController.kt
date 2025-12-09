package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.TypeOfDroneDto
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.TypeOfDrone
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.deny

@RequestMapping("/api/typeofdrone")
@RestController
class TypeOfDroneController : AbstractCommonRestController<TypeOfDrone, TypeOfDroneDto>(TypeOfDrone::class) {
    override fun remove(id: IdType) = deny()
}
