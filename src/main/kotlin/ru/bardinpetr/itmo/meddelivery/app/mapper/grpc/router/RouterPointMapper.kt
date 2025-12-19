package ru.bardinpetr.itmo.meddelivery.app.mapper.grpc.router

import ru.bardinpetr.itmo.meddelivery.app.service.router.*
import ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone as NoFlightZoneEntity
import ru.bardinpetr.itmo.meddelivery.app.entities.Point as EntityPoint
import ru.bardinpetr.itmo.meddelivery.app.service.router.Point as GrpcPoint

fun EntityPoint.toGrpc() = point {
    lat = this@toGrpc.lat
    lon = this@toGrpc.lon
}

fun GrpcPoint.toEnity() = EntityPoint(lat, lon)

fun planRouteRq(src: EntityPoint, dst: EntityPoint) = planRouteRequest {
    this.src = src.toGrpc()
    this.dst = dst.toGrpc()
}

fun PlanRouteResponse.toPointList() = routeList.map(GrpcPoint::toEnity)

fun noFlyZonesRq(list: List<NoFlightZoneEntity>) = updateNoFlightZonesRequest {
    zones.addAll(
        list.map {
            noFlightZone {
                center = it.center.toGrpc()
                radiusMeters = it.radius.toDouble()
            }
        }
    )
}
