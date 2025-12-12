package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.WarehouseDto
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse
import ru.bardinpetr.itmo.meddelivery.common.base.controller.AbstractCommonRestController
import ru.bardinpetr.itmo.meddelivery.common.base.controller.deny
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.models.IdType

@RequestMapping("/api/warehouse")
@RestController
class WarehouseController(service: AbstractBaseService<Warehouse>) :
    AbstractCommonRestController<Warehouse, WarehouseDto>(Warehouse::class, service) {
    override fun remove(id: IdType) = deny()
    override fun update(id: IdType, rq: WarehouseDto) = deny()
}
