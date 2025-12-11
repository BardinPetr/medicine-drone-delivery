package ru.bardinpetr.itmo.meddelivery.app.service.map

import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.maplibre.spatialk.geojson.dsl.buildFeature
import org.maplibre.spatialk.turf.measurement.distance
import org.maplibre.spatialk.units.extensions.inMeters
import ru.bardinpetr.itmo.meddelivery.app.entities.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse
import ru.bardinpetr.itmo.meddelivery.app.mapper.skPoint
import ru.bardinpetr.itmo.meddelivery.app.entities.Point as PointEntity

fun Drone.toGeoFeature() =
    buildFeature(location.skPoint) {
        setId(this@toGeoFeature.id!!.toString())
        properties = buildJsonObject {
            put("type", typeOfDrone.name)
            put("status", status.name)
        }
    }

fun MedicalFacility.toGeoFeature() =
    buildFeature(location.skPoint) {
        setId(this@toGeoFeature.id!!.toString())
        properties = buildJsonObject {
            put("name", name)
        }
    }

fun Warehouse.toGeoFeature() =
    buildFeature(location.skPoint) {
        setId(this@toGeoFeature.id!!.toString())
        properties = buildJsonObject {
            put("name", name)
        }
    }

fun NoFlightZone.toGeoFeature() =
    buildFeature(center.skPoint) {
        setId(this@toGeoFeature.id!!.toString())
        properties = buildJsonObject {
            put("radius", radius)
        }
    }
