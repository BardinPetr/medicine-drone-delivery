package ru.bardinpetr.itmo.islab1.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.islab1.common.auth.model.User
import ru.bardinpetr.itmo.islab1.common.models.IBaseEntity

@Entity
data class MedicalFacility(
    @Column(nullable = false)
    val name: String,

    @ManyToOne
    @JoinColumn(name = "responsible_user_id", nullable = true)
    val responsibleUser: User? = null,

    @Embedded
    val location: Point,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity