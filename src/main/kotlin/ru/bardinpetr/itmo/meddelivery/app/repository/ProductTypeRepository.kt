package ru.bardinpetr.itmo.meddelivery.app.repository

import ru.bardinpetr.itmo.meddelivery.app.entities.ProductType
import ru.bardinpetr.itmo.meddelivery.common.base.repo.ICommonRestRepository

interface ProductTypeRepository : ICommonRestRepository<ProductType> {
    fun findByType(typeName: String): ProductType?
}
