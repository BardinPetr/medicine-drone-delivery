package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone
import ru.bardinpetr.itmo.meddelivery.common.base.repo.ICommonRestRepository
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService

@Service
class NoFlightZoneService(repo: ICommonRestRepository<NoFlightZone>) :
    AbstractBaseService<NoFlightZone>(NoFlightZone::class, repo)
