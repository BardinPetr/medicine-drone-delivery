package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository

@Service
class MedicalFacilityService(repo: ICommonRestRepository<MedicalFacility>) :
    AbstractBaseService<MedicalFacility>(MedicalFacility::class, repo)
