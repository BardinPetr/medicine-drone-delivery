package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.TaskStatus
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity
import java.time.Instant

@Entity
data class FlightTask(
    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    var request: Request,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TaskStatus,

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    var productType: ProductType,

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = true)
    var warehouse: Warehouse? = null,

    @ManyToOne
    @JoinColumn(name = "medical_facility_id", nullable = true)
    var medicalFacility: MedicalFacility? = null,

    @Column(nullable = false)
    var quantity: Int = 0,

    @OneToMany(mappedBy = "flightTask")
    var drones: MutableList<Drone> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = true)
    var route: Route? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    var timestamp: Instant = Instant.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity
