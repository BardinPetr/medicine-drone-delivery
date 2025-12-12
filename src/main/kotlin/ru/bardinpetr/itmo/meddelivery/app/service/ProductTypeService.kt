package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.ProductType
import ru.bardinpetr.itmo.meddelivery.app.repository.ProductTypeRepository
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService

@Service
class ProductTypeService(override val repo: ProductTypeRepository) :
    AbstractBaseService<ProductType>(ProductType::class, repo) {
    fun getByCode(code: String): ProductType? = repo.findByType(code)
}
