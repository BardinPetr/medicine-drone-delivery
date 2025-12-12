package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.FlightTask
import ru.bardinpetr.itmo.meddelivery.app.repository.FlightTaskRepository
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService

@Service
class FlightTaskService(repo: FlightTaskRepository) : AbstractBaseService<FlightTask>(FlightTask::class, repo)
