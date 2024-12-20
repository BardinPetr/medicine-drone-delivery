
package ru.bardinpetr.itmo.meddelivery.common.auth.repository

import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository

interface MedicalFacilityRepository : ICommonRestRepository<MedicalFacility> {
    fun getFirstByName(name: String): MedicalFacility?

    fun getFirstByResponsibleUserId(userId: Long): MedicalFacility?
}
        