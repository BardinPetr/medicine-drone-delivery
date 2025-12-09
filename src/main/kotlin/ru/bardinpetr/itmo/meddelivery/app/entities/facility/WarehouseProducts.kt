package ru.bardinpetr.itmo.meddelivery.app.entities.facility

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.app.entities.product.WarehouseProductsId
import ru.bardinpetr.itmo.meddelivery.app.entities.product.ProductType
import ru.bardinpetr.itmo.meddelivery.common.models.ITypedBaseEntity
import java.io.Serializable

@Entity
data class WarehouseProducts(
    @EmbeddedId
    override var id: WarehouseProductsId?,

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    var product: ProductType,

    @ManyToOne
    @MapsId("warehouseId")
    @JoinColumn(name = "warehouse_id", nullable = false)
    var warehouse: Warehouse,

    @Column(nullable = false)
    var quantity: Int
) : Serializable, ITypedBaseEntity<WarehouseProductsId>
