package ru.bardinpetr.itmo.meddelivery.app.dto

import ru.bardinpetr.itmo.meddelivery.common.base.dto.IBaseDto

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.Route}
 */
data class RouteDto(
    val warehouseId: Long? = null,
    val medicalFacilityId: Long? = null,
    val routePoints: MutableList<RoutePointDto> = mutableListOf(),
    override val id: Long? = null
) : IBaseDto
