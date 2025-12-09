package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.common.models.ITypedBaseEntity

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
