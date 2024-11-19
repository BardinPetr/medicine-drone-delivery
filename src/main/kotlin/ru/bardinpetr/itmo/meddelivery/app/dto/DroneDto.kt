 package ru.bardinpetr.itmo.meddelivery.app.dto

 import ru.bardinpetr.itmo.meddelivery.app.entities.DroneStatus

 /**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.Drone}
 */
data class DroneDto(
     val typeOfDroneName: String?,
     val status: DroneStatus?,
     val locationLat: Double?,
     val locationLon: Double?,
     val flightTaskId: Long? = null,
     val id: Long? = null
 )