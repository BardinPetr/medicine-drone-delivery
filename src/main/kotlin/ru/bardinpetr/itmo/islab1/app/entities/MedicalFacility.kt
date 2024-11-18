package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.common.auth.model.User
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class MedicalFacility(
    @Column(nullable = false)
    val name: String,

    @ManyToOne
    @JoinColumn(name = "responsible_user_id", nullable = false)
    val responsibleUser: User,

    @Embedded
    val location: Point,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity