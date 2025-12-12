package ru.bardinpetr.itmo.meddelivery.app.repository

import ru.bardinpetr.itmo.meddelivery.app.entities.WarehouseProducts
import ru.bardinpetr.itmo.meddelivery.app.entities.WarehouseProductsId
import ru.bardinpetr.itmo.meddelivery.common.base.repo.IBaseCommonRestRepository
import ru.bardinpetr.itmo.meddelivery.common.models.IdType

interface WarehouseProductsRepository : IBaseCommonRestRepository<WarehouseProducts, WarehouseProductsId> {
    fun findByProductIdAndWarehouseId(productId: Long, warehouseId: Long): WarehouseProducts?
    fun findByProductIdAndQuantityGreaterThanEqualOrderByQuantityDesc(
        productType: IdType,
        quantity: Long
    ): List<WarehouseProducts>
}
