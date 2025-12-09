package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.ProductTypeDto
import ru.bardinpetr.itmo.meddelivery.app.entities.product.ProductType
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController

@RequestMapping("/api/producttype")
@RestController
class ProductTypeController(service: AbstractBaseService<ProductType>) :
    AbstractCommonRestController<ProductType, ProductTypeDto>(ProductType::class, service)
