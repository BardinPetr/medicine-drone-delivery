package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.ProductType
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository

@Service
class ProductTypeService(repo: ICommonRestRepository<ProductType>) :
    AbstractBaseService<ProductType>(ProductType::class, repo)
