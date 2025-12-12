package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.RequestEntryDto
import ru.bardinpetr.itmo.meddelivery.app.entities.RequestEntry
import ru.bardinpetr.itmo.meddelivery.common.base.controller.AbstractCommonRestController
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService

@RequestMapping("/api/requestentry")
@RestController
class RequestEntryController(service: AbstractBaseService<RequestEntry>) :
    AbstractCommonRestController<RequestEntry, RequestEntryDto>(RequestEntry::class, service)
