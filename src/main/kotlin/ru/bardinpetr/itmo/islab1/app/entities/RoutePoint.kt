package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.common.models.ITypedBaseEntity
import java.io.Serializable

@Entity
data class RoutePoint(
    @EmbeddedId
    override var id: RoutePointId?,

    @ManyToOne
    @MapsId("routeId")
    @JoinColumn(name = "route_id", nullable = false)
    val route: Route,

    @Embedded
    val location: Point,
) : ITypedBaseEntity<RoutePointId>

@Embeddable
data class RoutePointId(
    @Column(name = "route_id")
    val routeId: Long = 0,
    @Column(name = "point_number", nullable = false)
    val pointNumber: Int = 0,
) : Serializable