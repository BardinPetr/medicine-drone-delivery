package ru.bardinpetr.itmo.meddelivery.app.dto

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone}
 */
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseDto

data class NoFlightZoneDto(
    val radius: Float?,
    val centerLat: Double?,
    val centerLon: Double?,
    override val id: Long? = null
) : IBaseDto
