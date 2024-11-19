 package ru.bardinpetr.itmo.meddelivery.app.dto

 /**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.Route}
 */
data class RouteDto(
     val warehouseId: Long? = null,
     val medicalFacilityId: Long? = null,
     val routePoints: MutableList<RoutePointDto> = mutableListOf(),
     val id: Long? = null
 )