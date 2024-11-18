package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class Warehouse(
    @Column(nullable = false)
    val name: String,

    @Embedded
    val location: Point,

    @OneToMany(mappedBy = "warehouse")
    val products: List<WarehouseProducts> = listOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity