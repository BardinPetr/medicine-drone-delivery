package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.TypeOfDrone
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository

@Service
class TypeOfDroneService(repo: ICommonRestRepository<TypeOfDrone>) :
    AbstractBaseService<TypeOfDrone>(TypeOfDrone::class, repo)
