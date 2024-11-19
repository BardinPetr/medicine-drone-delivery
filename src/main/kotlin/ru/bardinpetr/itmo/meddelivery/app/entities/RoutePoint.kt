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
    var route: Route,

    @Embedded
    var location: Point,
) : ITypedBaseEntity<RoutePointId>

@Embeddable
data class RoutePointId(
    @Column(name = "route_id")
    var routeId: Long = 0,
    @Column(name = "point_number", nullable = false)
    var pointNumber: Int = 0,
) : Serializable