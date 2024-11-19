package ru.bardinpetr.itmo.meddelivery.app.dto

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse}
 */
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseDto

data class WarehouseDto(
    val name: String?,
    val locationLat: Double?,
    val locationLon: Double?,
    val products: MutableList<WarehouseProductsDto> = mutableListOf(),
    override val id: Long? = null
) : IBaseDto