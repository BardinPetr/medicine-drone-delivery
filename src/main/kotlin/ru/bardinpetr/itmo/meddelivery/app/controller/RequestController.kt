package ru.bardinpetr.itmo.meddelivery.app.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.RequestDto
import ru.bardinpetr.itmo.meddelivery.app.entities.Request
import ru.bardinpetr.itmo.meddelivery.app.mapper.RequestEntryMapper
import ru.bardinpetr.itmo.meddelivery.app.service.RequestService
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.deny

@RequestMapping("/api/request")
@RestController
class RequestController(
    private val requestEntryMapper: RequestEntryMapper,
    override val service: RequestService
) : AbstractCommonRestController<Request, RequestDto>(Request::class, service) {

    override fun update(id: IdType, rq: RequestDto): RequestDto = deny()

    @PostMapping
    override fun create(@Valid @RequestBody rq: RequestDto): RequestDto =
        rq.requestEntries
            .map(requestEntryMapper::toEntity)
            .let(service::makeOrder)
            .let(mapper::toDto)
}
