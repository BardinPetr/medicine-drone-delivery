package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import java.io.Serializable

@Entity
data class WarehouseProducts(
    @EmbeddedId
    val id: WarehouseProductsId,

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    val product: ProductType,

    @ManyToOne
    @MapsId("warehouseId")
    @JoinColumn(name = "warehouse_id", nullable = false)
    val warehouse: Warehouse,

    @Column(nullable = false)
    val quantity: Int,

    @Column(nullable = false)
    val reservedQuantity: Int
) : Serializable

@Embeddable
data class WarehouseProductsId(
    @Column(name = "product_id")
    val productId: Long = 0,
    @Column(name = "warehouse_id")
    val warehouseId: Long = 0
) : Serializable