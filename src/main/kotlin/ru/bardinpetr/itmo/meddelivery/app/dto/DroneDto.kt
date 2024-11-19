package ru.bardinpetr.itmo.meddelivery.app.dto

 import ru.bardinpetr.itmo.meddelivery.app.entities.DroneStatus

 /**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.Drone}
 */
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseDto

data class DroneDto(
     val typeOfDroneName: String?,
     val status: DroneStatus?,
     val locationLat: Double?,
     val locationLon: Double?,
     val flightTaskId: Long? = null,
     override val id: Long? = null
 ) : IBaseDto