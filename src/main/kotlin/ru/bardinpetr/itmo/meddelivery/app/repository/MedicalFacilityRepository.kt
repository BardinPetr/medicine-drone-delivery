package ru.bardinpetr.itmo.meddelivery.app.repository

import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.common.base.repo.ICommonRestRepository

interface MedicalFacilityRepository : ICommonRestRepository<MedicalFacility> {
    fun getFirstByName(name: String): MedicalFacility?

    fun getFirstByResponsibleUserId(userId: Long): MedicalFacility?
}
