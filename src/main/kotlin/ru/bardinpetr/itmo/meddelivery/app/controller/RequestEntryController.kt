package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.RequestEntryDto
import ru.bardinpetr.itmo.meddelivery.app.entities.product.RequestEntry
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController

@RequestMapping("/api/requestentry")
@RestController
class RequestEntryController(service: AbstractBaseService<RequestEntry>) :
    AbstractCommonRestController<RequestEntry, RequestEntryDto>(RequestEntry::class, service)
