package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.RequestEntry
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse
import ru.bardinpetr.itmo.meddelivery.app.entities.WarehouseProducts
import ru.bardinpetr.itmo.meddelivery.app.entities.WarehouseProductsId
import ru.bardinpetr.itmo.meddelivery.app.repository.ProductTypeRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.WarehouseProductsRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.WarehouseRepository
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.utils.error.NotFoundException
import ru.bardinpetr.itmo.meddelivery.common.ws.NotifyChangeType
import kotlin.jvm.optionals.getOrElse

@Service
class WarehouseService(
    private val productsRepo: WarehouseProductsRepository,
    private val productTypeRepo: ProductTypeRepository,
    private val warehouseRepo: WarehouseRepository,
    repo: WarehouseRepository
) : AbstractBaseService<Warehouse>(Warehouse::class, repo) {

    fun getProducts(
        spec: Specification<WarehouseProducts>?,
        pageable: Pageable = Pageable.unpaged()
    ): Page<WarehouseProducts> = productsRepo.findAll(spec, pageable)

    fun getProducts(): List<WarehouseProducts> = productsRepo.findAll()

    fun setProductQuantity(productId: IdType, warehouseId: IdType, newQuantity: Int): WarehouseProducts =
        (
            productsRepo.findByProductIdAndWarehouseId(productId, warehouseId)
                ?: WarehouseProducts(
                    id = WarehouseProductsId(productId, warehouseId),
                    product = productTypeRepo.findById(productId).getOrElse { throw NotFoundException() },
                    warehouse = warehouseRepo.findById(warehouseId).getOrElse { throw NotFoundException() },
                    quantity = newQuantity
                )
            )
            .apply { quantity = newQuantity }
            .let(productsRepo::save)
            .also { notifier.notifyChanges(WarehouseProducts::class, 0, NotifyChangeType.MOD) }

    fun findAvailableWarehouses(rq: RequestEntry): List<WarehouseProducts> =
        productsRepo.findByProductIdAndQuantityGreaterThanEqualOrderByQuantityDesc(rq.productType.id!!, 0L)
}
