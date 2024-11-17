package ru.bardinpetr.itmo.islab1.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.islab1.common.models.IBaseEntity

@Entity
data class FlightTask(
    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    val request: Request,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: TaskStatus,

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    val productType: ProductType,

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = true)
    val warehouse: Warehouse? = null,

    @ManyToOne
    @JoinColumn(name = "medical_facility_id", nullable = true)
    val medicalFacility: MedicalFacility? = null,

    @Column(nullable = false)
    val quantity: Int,

    @OneToMany(mappedBy = "flightTask")
    val drones: List<Drone> = listOf(),

    @OneToMany(mappedBy = "flightTask")
    val routePoints: List<RoutePoint> = listOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity