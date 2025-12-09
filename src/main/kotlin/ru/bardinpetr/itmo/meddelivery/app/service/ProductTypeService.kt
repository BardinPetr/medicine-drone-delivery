package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.ProductType
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService

@Service
class ProductTypeService : AbstractBaseService<ProductType>(ProductType::class)
