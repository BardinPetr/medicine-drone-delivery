package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.FlightTaskDto
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.FlightTask
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController

@RequestMapping("/api/flighttask")
@RestController
class FlightTaskController : AbstractCommonRestController<FlightTask, FlightTaskDto>(FlightTask::class) {
    override fun create(rq: FlightTaskDto) = throw IllegalAccessException("Not available")
    override fun remove(id: IdType) = throw IllegalAccessException("Not available")
    override fun update(id: IdType, rq: FlightTaskDto) = throw IllegalAccessException("Not available")
}
