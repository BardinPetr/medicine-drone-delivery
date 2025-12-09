package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.Request
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService

@Service
class RequestService : AbstractBaseService<Request>(Request::class)
