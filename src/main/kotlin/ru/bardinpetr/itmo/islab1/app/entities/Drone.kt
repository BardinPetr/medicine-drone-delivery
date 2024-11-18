package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class Drone(
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    val typeOfDrone: TypeOfDrone,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: DroneStatus,

    @Embedded
    val location: Point,

    @ManyToOne(optional = true)
    @JoinColumn(name = "flight_task_id")
    val flightTask: FlightTask? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity

enum class DroneStatus {
    IDLE, FLYING_TO, FLYING_FROM
}