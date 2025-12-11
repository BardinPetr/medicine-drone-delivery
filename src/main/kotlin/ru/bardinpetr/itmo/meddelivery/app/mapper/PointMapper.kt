package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.maplibre.spatialk.geojson.Point
import ru.bardinpetr.itmo.meddelivery.app.entities.Point as PointEntity

val PointEntity.skPoint: Point
    get() = Point(lon, lat)

val Point.ePoint: PointEntity
    get() = PointEntity(latitude, longitude)
