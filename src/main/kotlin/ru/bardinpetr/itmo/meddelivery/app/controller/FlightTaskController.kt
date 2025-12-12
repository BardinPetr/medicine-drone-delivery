package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.FlightTaskDto
import ru.bardinpetr.itmo.meddelivery.app.entities.FlightTask
import ru.bardinpetr.itmo.meddelivery.common.base.controller.AbstractCommonRestController
import ru.bardinpetr.itmo.meddelivery.common.base.controller.deny
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.models.IdType

@RequestMapping("/api/flighttask")
@RestController
class FlightTaskController(service: AbstractBaseService<FlightTask>) :
    AbstractCommonRestController<FlightTask, FlightTaskDto>(FlightTask::class, service) {
    override fun create(rq: FlightTaskDto) = deny()
    override fun remove(id: IdType) = deny()
    override fun update(id: IdType, rq: FlightTaskDto) = deny()
}
