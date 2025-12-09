package ru.bardinpetr.itmo.meddelivery.app.entities.product

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class ProductType(
    @Column(nullable = false)
    var type: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity
