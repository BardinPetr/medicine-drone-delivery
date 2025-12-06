package ru.bardinpetr.itmo.meddelivery.app.modules.map.service

import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.Point
import io.github.dellisd.spatialk.geojson.Position
import io.github.dellisd.spatialk.geojson.dsl.feature
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.geo.NoFlightZone


fun Drone.toGeoFeature(): Feature? {
    return feature(
        id = id.toString(),
        geometry = Point(Position(location.lon, location.lat))
    ) {
        put("type", typeOfDrone.name)
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