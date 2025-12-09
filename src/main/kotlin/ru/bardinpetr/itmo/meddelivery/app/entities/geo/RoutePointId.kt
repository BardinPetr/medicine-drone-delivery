package ru.bardinpetr.itmo.meddelivery.app.entities.geo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class RoutePointId(
    @Column(name = "route_id")
    var routeId: Long = 0,
    @Column(name = "point_number", nullable = false)
    var pointNumber: Int = 0,
) : Serializable
