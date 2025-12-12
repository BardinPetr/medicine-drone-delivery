package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class NoFlightZone(
    @Column(nullable = false)
    var radius: Float,

    @Embedded
    var center: Point,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity
