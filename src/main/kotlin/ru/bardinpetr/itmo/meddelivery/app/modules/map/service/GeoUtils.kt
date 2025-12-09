package ru.bardinpetr.itmo.meddelivery.app.modules.map.service

import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.Point
import io.github.dellisd.spatialk.geojson.Position
import io.github.dellisd.spatialk.geojson.dsl.feature
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.geo.NoFlightZone
import java.lang.Math.toRadians
import kotlin.math.*
import ru.bardinpetr.itmo.meddelivery.app.entities.geo.Point as PointEntity


fun Drone.toGeoFeature(): Feature? {
    return feature(
        id = id.toString(),
        geometry = Point(Position(location.lon, location.lat))
    ) {
        put("type", typeOfDrone.name)
        put("status", status.name)
    }
}

fun MedicalFacility.toGeoFeature(): Feature? {
    return feature(
        id = id.toString(),
        geometry = Point(Position(location.lon, location.lat))
    ) {
        put("name", name)
    }
}

fun Warehouse.toGeoFeature(): Feature? {
    return feature(
        id = id.toString(),
        geometry = Point(Position(location.lon, location.lat))
    ) {
        put("name", name)
    }
}


fun NoFlightZone.toGeoFeature(): Feature? {
    return feature(
        id = id.toString(),
        geometry = Point(Position(center.lon, center.lat))
    ) {
        put("radius", radius)
    }
}


fun PointEntity.distance(other: PointEntity): Double {
    val lat1 = toRadians(lat)
    val lon1 = toRadians(lon)
    val lat2 = toRadians(other.lat)
    val lon2 = toRadians(other.lon)
    val dLat = lat2 - lat1
    val dLon = lon2 - lon1
    val a = sin(dLat / 2).pow(2.0) + cos(lat1) * cos(lat2) * sin(dLon / 2).pow(2.0)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return 6371000.0 * c
}

fun NoFlightZone.containsPoint(point: PointEntity): Boolean =
    center.distance(point) <= this.radius

