package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class ProductType(
    @Column(nullable = false)
    var type: String,

    @OneToMany(mappedBy = "product")
    var warehouses: MutableList<WarehouseProducts> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity