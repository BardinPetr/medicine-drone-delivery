package ru.bardinpetr.itmo.meddelivery.app.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import ru.bardinpetr.itmo.meddelivery.app.dto.WarehouseProductsDto
import ru.bardinpetr.itmo.meddelivery.app.entities.facility.WarehouseProducts
import ru.bardinpetr.itmo.meddelivery.app.mapper.WarehouseProductsMapper
import ru.bardinpetr.itmo.meddelivery.app.repository.WarehouseProductsRepository
import ru.bardinpetr.itmo.meddelivery.common.handling.EnableResponseWrapper
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.rest.search.FilterModel
import ru.bardinpetr.itmo.meddelivery.common.utils.error.NotFoundException
import ru.bardinpetr.itmo.meddelivery.common.ws.NotifyChangeType
import ru.bardinpetr.itmo.meddelivery.common.ws.WebSocketNotifyService

@RequestMapping("/api/warehouseproducts")
@RestController
@EnableResponseWrapper
class WarehouseProductsController(
    private val notifier: WebSocketNotifyService,
    private val mapper: WarehouseProductsMapper,
    private val repository: WarehouseProductsRepository
) {
    @GetMapping
    fun list(pageable: Pageable, @RequestParam filter: FilterModel?): Page<WarehouseProductsDto> =
        repository
            .findAll(filter?.asSpec(), pageable)
            .map(mapper::toDto)

    @PostMapping
    fun create(@Valid @RequestBody rq: WarehouseProductsDto): WarehouseProductsDto =
        rq
            .also {
                require(repository.findByProductIdAndWarehouseId(rq.product?.id!!, rq.warehouse?.id!!) == null) {
                    "Existing product"
                }
            }
            .let(mapper::toEntity)
            .let(repository::save)
            .let(mapper::toDto)
            .also { notifier.notifyChanges(WarehouseProducts::class, 0, NotifyChangeType.ADD) }

    @PutMapping("/{id}")
    fun update(@PathVariable id: IdType, @Valid @RequestBody rq: WarehouseProductsDto): WarehouseProductsDto =
        repository
            .findByProductIdAndWarehouseId(rq.product?.id!!, rq.warehouse?.id!!)
            ?.apply {
                require(rq.quantity!! >= 1) { "Invalid quantity" }
                quantity = rq.quantity
            }
            ?.let(repository::save)
            ?.let(mapper::toDto)
            ?.also { notifier.notifyChanges(WarehouseProducts::class, 0, NotifyChangeType.MOD) }
            ?: throw NotFoundException()
}
