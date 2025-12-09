package ru.bardinpetr.itmo.meddelivery.app.entities.geo

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class Route(
    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    var warehouse: Warehouse,

    @ManyToOne
    @JoinColumn(name = "medical_facility_id", nullable = false)
    var medicalFacility: MedicalFacility,

    @OneToMany(mappedBy = "route", cascade = [CascadeType.ALL], orphanRemoval = true)
    var routePoints: MutableList<RoutePoint> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity
