package ru.bardinpetr.itmo.meddelivery.app.entities.product

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.TaskStatus
import ru.bardinpetr.itmo.meddelivery.common.auth.model.User
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class Request(
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TaskStatus,

    @ManyToOne
    @JoinColumn(name = "medical_facility_id", nullable = false)
    var medicalFacility: MedicalFacility?,

    @OneToMany(mappedBy = "request")
    var requestEntries: MutableList<RequestEntry> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity