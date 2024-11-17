package ru.bardinpetr.itmo.islab1.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.islab1.common.models.IBaseEntity

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
    IDLE, ASSIGNED, IN_FLIGHT, RETURNING
}