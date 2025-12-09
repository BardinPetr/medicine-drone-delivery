package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.RouteDto
import ru.bardinpetr.itmo.meddelivery.app.entities.Route
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController

@RequestMapping("/api/route")
@RestController
class RouteController(service: AbstractBaseService<Route>) :
    AbstractCommonRestController<Route, RouteDto>(Route::class, service)
