package ru.bardinpetr.itmo.meddelivery.app.repository

import ru.bardinpetr.itmo.meddelivery.app.entities.product.WarehouseProductsId
import ru.bardinpetr.itmo.meddelivery.app.entities.facility.WarehouseProducts
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseCommonRestRepository

interface WarehouseProductsRepository : IBaseCommonRestRepository<WarehouseProducts, WarehouseProductsId> {
    fun findByProductIdAndWarehouseId(productId: Long, warehouseId: Long): WarehouseProducts?
}
