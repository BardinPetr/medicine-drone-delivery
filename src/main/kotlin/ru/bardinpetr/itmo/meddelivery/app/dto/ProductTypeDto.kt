package ru.bardinpetr.itmo.meddelivery.app.dto

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.ProductType}
 */
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseDto

data class ProductTypeDto(
    val type: String?,
    override val id: Long? = null
) : IBaseDto
