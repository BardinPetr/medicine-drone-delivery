package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class WarehouseProductsId(
    @Column(name = "product_id")
    var productId: Long = 0,
    @Column(name = "warehouse_id")
    var warehouseId: Long = 0
) : Serializable