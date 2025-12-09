package ru.bardinpetr.itmo.meddelivery.app.dto

import ru.bardinpetr.itmo.meddelivery.app.entities.drones.DroneStatus
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseDto

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.Drone}
 */
data class DroneDto(
    val typeOfDrone: TypeOfDroneDto?,
    val status: DroneStatus? = DroneStatus.IDLE,
    val locationLat: Double? = 0.0,
    val locationLon: Double? = 0.0,
    val flightTask: FlightTaskDto? = null,
    override val id: Long? = null
) : IBaseDto
