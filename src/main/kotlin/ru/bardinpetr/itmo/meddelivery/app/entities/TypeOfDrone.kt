package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class TypeOfDrone(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var maxWeight: Long,

    @Column(nullable = false)
    var speed: Double,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity
