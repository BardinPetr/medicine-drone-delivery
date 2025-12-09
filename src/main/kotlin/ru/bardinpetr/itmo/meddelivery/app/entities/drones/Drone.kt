package ru.bardinpetr.itmo.meddelivery.app.entities.drones

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.app.entities.geo.Point
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class Drone(
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    var typeOfDrone: TypeOfDrone,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: DroneStatus,

    @Embedded
    var location: Point,

    @ManyToOne(optional = true)
    @JoinColumn(name = "flight_task_id")
    var flightTask: FlightTask? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity
