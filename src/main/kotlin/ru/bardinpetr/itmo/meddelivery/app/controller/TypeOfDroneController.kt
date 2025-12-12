package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.TypeOfDroneDto
import ru.bardinpetr.itmo.meddelivery.app.entities.TypeOfDrone
import ru.bardinpetr.itmo.meddelivery.common.base.controller.AbstractCommonRestController
import ru.bardinpetr.itmo.meddelivery.common.base.controller.deny
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.models.IdType

@RequestMapping("/api/typeofdrone")
@RestController
class TypeOfDroneController(service: AbstractBaseService<TypeOfDrone>) :
    AbstractCommonRestController<TypeOfDrone, TypeOfDroneDto>(TypeOfDrone::class, service) {
    override fun remove(id: IdType) = deny()
}
