package ru.bardinpetr.itmo.islab1.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.islab1.common.models.IBaseEntity
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

    @Column(nullable = false, name = "point_number")
    @MapsId("pointNumber")
    val pointNumber: Int
)

@Embeddable
data class RoutePointId(
    @Column(name = "flight_task_id")
    val flightTaskId: Long = 0,
    @Column(name = "point_number")
    val pointNumber: Int = 0,
) : Serializable