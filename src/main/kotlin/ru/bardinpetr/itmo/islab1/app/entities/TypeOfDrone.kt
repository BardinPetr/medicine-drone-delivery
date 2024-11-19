package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class TypeOfDrone(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val maxWeight: Long,

    @Column(nullable = false)
    val speed: Double,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity