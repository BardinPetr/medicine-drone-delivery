package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.NoFlightZoneDto
import ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController

@RequestMapping("/api/noflightzone")
@RestController
class NoFlightZoneController(service: AbstractBaseService<NoFlightZone>) :
    AbstractCommonRestController<NoFlightZone, NoFlightZoneDto>(NoFlightZone::class, service)
