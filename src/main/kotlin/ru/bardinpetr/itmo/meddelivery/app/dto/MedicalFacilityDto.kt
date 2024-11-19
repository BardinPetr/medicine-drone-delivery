 package ru.bardinpetr.itmo.meddelivery.app.dto

 /**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility}
 */
data class MedicalFacilityDto(
     val name: String?,
     val responsibleUserUsername: String?,
     val locationLat: Double?,
     val locationLon: Double?,
     val id: Long? = null
 )