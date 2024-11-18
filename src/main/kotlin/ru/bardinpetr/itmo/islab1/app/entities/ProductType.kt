package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class ProductType(
    @Column(nullable = false)
    val type: String,

    @Column(nullable = false)
    val pieceWeight: Float,

    @OneToMany(mappedBy = "product")
    val warehouses: List<WarehouseProducts> = listOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity