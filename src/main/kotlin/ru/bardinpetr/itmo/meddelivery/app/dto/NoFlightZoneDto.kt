package ru.bardinpetr.itmo.meddelivery.app.dto

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone}
 */
data class NoFlightZoneDto(val radius: Float?, val centerLat: Double?, val centerLon: Double?, val id: Long? = null)