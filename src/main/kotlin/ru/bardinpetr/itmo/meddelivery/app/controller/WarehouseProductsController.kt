package ru.bardinpetr.itmo.meddelivery.app.controller

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import ru.bardinpetr.itmo.meddelivery.app.dto.WarehouseProductsDto
import ru.bardinpetr.itmo.meddelivery.app.entities.WarehouseProducts
import ru.bardinpetr.itmo.meddelivery.app.mapper.WarehouseProductsMapper
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.WarehouseProductsRepository
import ru.bardinpetr.itmo.meddelivery.common.handling.EnableResponseWrapper
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.rest.search.FilterModel
import ru.bardinpetr.itmo.meddelivery.common.utils.error.NotFoundException
import ru.bardinpetr.itmo.meddelivery.common.ws.NotifyChangeType
import ru.bardinpetr.itmo.meddelivery.common.ws.WebSocketNotifyService

@RequestMapping("/api/warehouseproducts")
@RestController
@EnableResponseWrapper
class WarehouseProductsController {

    @Autowired
    protected lateinit var notifier: WebSocketNotifyService

    @Autowired
    protected lateinit var mapper: WarehouseProductsMapper

    @Autowired
    protected lateinit var repository: WarehouseProductsRepository

    @GetMapping
    fun list(pageable: Pageable, @RequestParam filter: FilterModel?): Page<WarehouseProductsDto> =
        repository
            .findAll(filter?.asSpec(), pageable)
            .map(mapper::toDto)

    @PostMapping
    fun create(@Valid @RequestBody rq: WarehouseProductsDto): WarehouseProductsDto =
        rq
            .also {
                if(repository.findByProductIdAndWarehouseId(rq.product?.id!!, rq.warehouse?.id!!) != null)
                    throw IllegalArgumentException("Existing product")
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
                if (rq.quantity!! < 1) throw IllegalArgumentException("Invalid quantity")
                quantity = rq.quantity
            }
            ?.let(repository::save)
            ?.let(mapper::toDto)
            ?.also { notifier.notifyChanges(WarehouseProducts::class, 0, NotifyChangeType.MOD) }
            ?: throw NotFoundException()
}
