package ru.bardinpetr.itmo.meddelivery.app.dto

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse}
 */
data class WarehouseDto(
    val name: String?,
    val locationLat: Double?,
    val locationLon: Double?,
    val products: MutableList<WarehouseProductsDto> = mutableListOf(),
    val id: Long? = null
)