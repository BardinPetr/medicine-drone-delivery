package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import org.hibernate.envers.RevisionTimestamp
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity
import java.time.Instant

@Entity
data class Route(
    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    val warehouse: Warehouse,

    @ManyToOne
    @JoinColumn(name = "medical_facility_id", nullable = false)
    val medicalFacility: MedicalFacility,

    @OneToMany(mappedBy = "route", cascade = [CascadeType.ALL], orphanRemoval = true)
    val routePoints: List<RoutePoint> = listOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity
