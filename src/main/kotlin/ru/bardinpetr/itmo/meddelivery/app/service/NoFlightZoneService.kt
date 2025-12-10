package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository

@Service
class NoFlightZoneService(repo: ICommonRestRepository<NoFlightZone>) :
    AbstractBaseService<NoFlightZone>(NoFlightZone::class, repo)
