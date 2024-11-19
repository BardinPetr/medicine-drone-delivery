
package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.ProductTypeDto
import ru.bardinpetr.itmo.meddelivery.app.entities.ProductType
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController

@RequestMapping("/api/producttype")
@RestController
class ProductTypeController : AbstractCommonRestController<ProductType, ProductTypeDto>(ProductType::class)
        