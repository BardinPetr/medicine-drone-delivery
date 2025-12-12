package ru.bardinpetr.itmo.meddelivery.app.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import ru.bardinpetr.itmo.meddelivery.app.dto.WarehouseProductsDto
import ru.bardinpetr.itmo.meddelivery.app.events.EventSenderService
import ru.bardinpetr.itmo.meddelivery.app.mapper.WarehouseProductsMapper
import ru.bardinpetr.itmo.meddelivery.app.service.WarehouseService
import ru.bardinpetr.itmo.meddelivery.common.handling.EnableResponseWrapper
import ru.bardinpetr.itmo.meddelivery.common.search.FilterModel

@RequestMapping("/api/warehouseproducts")
@RestController
@EnableResponseWrapper
class WarehouseProductsController(
    private val mapper: WarehouseProductsMapper,
    private val warehouseService: WarehouseService,
    private val evt: EventSenderService,
) {
    @GetMapping
    fun list(pageable: Pageable, @RequestParam filter: FilterModel?): Page<WarehouseProductsDto> =
        warehouseService
            .getProducts(filter?.asSpec(), pageable)
            .map(mapper::toDto)

    @PostMapping
    fun create(@Valid @RequestBody rq: WarehouseProductsDto): WarehouseProductsDto {
        require(rq.quantity!! >= 0) { "Invalid quantity" }
        return warehouseService
            .setProductQuantity(rq.product?.id!!, rq.warehouse?.id!!, rq.quantity)
            .let(mapper::toDto)
            .also { evt.sendProcessPlans() }
    }

    @PutMapping("/{id}")
    fun update(@Valid @RequestBody rq: WarehouseProductsDto): WarehouseProductsDto = create(rq)
}
