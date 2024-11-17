package ru.bardinpetr.itmo.islab1.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.islab1.common.auth.model.User
import ru.bardinpetr.itmo.islab1.common.models.IBaseEntity

@Entity
data class Request(
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: TaskStatus,

    @ManyToOne
    @JoinColumn(name = "medical_facility_id", nullable = false)
    val medicalFacility: MedicalFacility,

    @OneToMany(mappedBy = "request")
    val requestEntries: List<RequestEntry> = listOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity