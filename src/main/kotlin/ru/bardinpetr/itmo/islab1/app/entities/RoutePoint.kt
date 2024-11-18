package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import java.io.Serializable

@Entity
data class RoutePoint(
    @EmbeddedId
    val id: RoutePointId,

    @ManyToOne
    @MapsId("flightTaskId")
    @JoinColumn(name = "flight_task_id", nullable = false)
    val flightTask: FlightTask,

    @Embedded
    val location: Point,
)

@Embeddable
data class RoutePointId(
    @Column(name = "flight_task_id")
    val flightTaskId: Long = 0,
    @Column(name = "point_number", nullable = false)
    val pointNumber: Int = 0,
) : Serializable