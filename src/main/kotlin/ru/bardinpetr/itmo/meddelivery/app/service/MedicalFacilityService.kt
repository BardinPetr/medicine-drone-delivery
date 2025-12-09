package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService

@Service
class MedicalFacilityService : AbstractBaseService<MedicalFacility>(MedicalFacility::class)
