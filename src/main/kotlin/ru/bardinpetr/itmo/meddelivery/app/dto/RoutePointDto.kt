package ru.bardinpetr.itmo.meddelivery.app.dto

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.RoutePoint}
 */
data class RoutePointDto(
    val idRouteId: Long = 0,
    val idPointNumber: Int = 0,
    val locationLat: Double?,
    val locationLon: Double?
)
