package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.app.entities.geo.Point
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class Warehouse(
    @Column(nullable = false)
    var name: String,

    @Embedded
    var location: Point,

    @OneToMany(mappedBy = "warehouse")
    var products: MutableList<WarehouseProducts> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity