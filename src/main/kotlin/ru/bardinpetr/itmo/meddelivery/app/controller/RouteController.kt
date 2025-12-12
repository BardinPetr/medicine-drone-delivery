package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.RouteDto
import ru.bardinpetr.itmo.meddelivery.app.entities.Route
import ru.bardinpetr.itmo.meddelivery.common.base.controller.AbstractCommonRestController
import ru.bardinpetr.itmo.meddelivery.common.base.controller.deny
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.models.IdType

@RequestMapping("/api/route")
@RestController
class RouteController(service: AbstractBaseService<Route>) :
    AbstractCommonRestController<Route, RouteDto>(Route::class, service) {
    override fun create(rq: RouteDto) = deny()
    override fun remove(id: IdType) = deny()
    override fun update(id: IdType, rq: RouteDto) = deny()
}
