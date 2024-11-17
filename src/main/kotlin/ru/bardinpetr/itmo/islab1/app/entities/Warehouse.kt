package ru.bardinpetr.itmo.islab1.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.islab1.common.models.IBaseEntity

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