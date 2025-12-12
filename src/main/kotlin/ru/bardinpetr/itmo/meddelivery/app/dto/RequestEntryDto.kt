package ru.bardinpetr.itmo.meddelivery.app.dto

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.RequestEntry}
 */
import ru.bardinpetr.itmo.meddelivery.common.base.dto.IBaseDto

data class RequestEntryDto(
    val requestId: Long? = null,
    val productTypeType: String?,
    val quantity: Int?,
    val fulfilledQuantity: Int = 0,
    override val id: Long? = null
) : IBaseDto
