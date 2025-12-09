package ru.bardinpetr.itmo.meddelivery.app.entities.facility

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.app.entities.geo.Point
import ru.bardinpetr.itmo.meddelivery.common.auth.model.User
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class MedicalFacility(
    @Column(nullable = false)
    var name: String,

    @ManyToOne
    @JoinColumn(name = "responsible_user_id", nullable = false)
    var responsibleUser: User,

    @Embedded
    var location: Point,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity
