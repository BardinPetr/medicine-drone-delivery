package ru.bardinpetr.itmo.meddelivery.app.dto

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.TypeOfDrone}
 */
import ru.bardinpetr.itmo.meddelivery.common.base.dto.IBaseDto

data class TypeOfDroneDto(
    val name: String?,
    val maxWeight: Long?,
    val speed: Double?,
    override val id: Long? = null
) : IBaseDto
