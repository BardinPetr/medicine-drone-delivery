package ru.bardinpetr.itmo.islab1.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.islab1.common.models.IBaseEntity

@Entity
data class TypeOfDrone(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val maxWeight: Float,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity