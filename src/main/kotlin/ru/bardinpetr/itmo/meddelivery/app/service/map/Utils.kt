package ru.bardinpetr.itmo.meddelivery.app.service.map

import kotlinx.serialization.json.JsonObject
import org.maplibre.spatialk.geojson.FeatureCollection
import org.maplibre.spatialk.geojson.Point
import org.maplibre.spatialk.geojson.Position
import org.maplibre.spatialk.geojson.dsl.buildFeature
import org.maplibre.spatialk.turf.measurement.distance
import org.maplibre.spatialk.units.extensions.inMeters
import ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone
import ru.bardinpetr.itmo.meddelivery.app.mapper.skPoint
import ru.bardinpetr.itmo.meddelivery.app.entities.Point as PointEntity

fun NoFlightZone.containsPoint(point: PointEntity): Boolean =
    distance(center.skPoint, point.skPoint).inMeters <= this.radius

// lat, lon
fun Pair<Double, Double>.toKPoint(): Point = Point(this.second, this.first)

val Position.pt get() = Point(this)

val Point.pos get() = coordinates

val Collection<Point>.featureCollection
    get() = FeatureCollection(map { buildFeature<Point, JsonObject>(it) })
