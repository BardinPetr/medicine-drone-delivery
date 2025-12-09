package ru.bardinpetr.itmo.meddelivery.app.repository

import ru.bardinpetr.itmo.meddelivery.app.entities.product.ProductType
import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository

interface ProductTypeRepository : ICommonRestRepository<ProductType> {
    fun findByType(typeName: String): ProductType?
}
