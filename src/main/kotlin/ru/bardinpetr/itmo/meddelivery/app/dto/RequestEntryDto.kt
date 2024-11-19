package ru.bardinpetr.itmo.meddelivery.app.dto

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.RequestEntry}
 */
data class RequestEntryDto(
    val requestId: Long? = null,
    val productTypeType: String?,
    val quantity: Int?,
    val fulfilledQuantity: Int = 0,
    val id: Long? = null
)