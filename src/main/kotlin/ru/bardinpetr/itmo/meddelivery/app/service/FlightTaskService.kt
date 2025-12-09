
package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.FlightTask
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService

@Service
class FlightTaskService : AbstractBaseService<FlightTask>(FlightTask::class)
