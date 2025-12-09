package ru.bardinpetr.itmo.meddelivery.app.dto

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.WarehouseProducts}
 */
data class WarehouseProductsDto(
    val idProductId: Long = 0,
    val idWarehouseId: Long = 0,
    val product: ProductTypeDto?,
    val warehouse: WarehouseDto?,
    val quantity: Int?
)