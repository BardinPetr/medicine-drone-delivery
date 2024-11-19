package ru.bardinpetr.itmo.meddelivery.app.dto

 /**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility}
 */
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseDto

data class MedicalFacilityDto(
     val name: String?,
     val responsibleUserUsername: String?,
     val locationLat: Double?,
     val locationLon: Double?,
     override val id: Long? = null
 ) : IBaseDto