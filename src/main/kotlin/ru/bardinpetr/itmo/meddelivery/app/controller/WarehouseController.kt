package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.WarehouseDto
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController

@RequestMapping("/api/warehouse")
@RestController
class WarehouseController : AbstractCommonRestController<Warehouse, WarehouseDto>(Warehouse::class) {
    override fun remove(id: IdType) = throw IllegalAccessException("Not available")
    override fun update(id: IdType, rq: WarehouseDto) = throw IllegalAccessException("Not available")
}
        