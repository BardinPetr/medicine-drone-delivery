package ru.bardinpetr.itmo.meddelivery.app.modules.map.service


import io.github.dellisd.spatialk.geojson.*
import io.github.dellisd.spatialk.geojson.dsl.feature
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.*

@Service
class MapService(
    private val droneRepository: DroneRepository,
    private val medicalFacilityRepository: MedicalFacilityRepository,
    private val warehouseRepository: WarehouseRepository,
    private val zoneRepository: NoFlightZoneRepository,
) {
    fun getDrones(): FeatureCollection =
        droneRepository
            .findAll()
            .mapNotNull(Drone::toGeoFeature)
            .let(::FeatureCollection)

    fun getMedicalFacilities(): FeatureCollection =
        medicalFacilityRepository
            .findAll()
            .mapNotNull(MedicalFacility::toGeoFeature)
            .let(::FeatureCollection)

    fun getWarehouses(): FeatureCollection =
        warehouseRepository
            .findAll()
            .mapNotNull(Warehouse::toGeoFeature)
            .let(::FeatureCollection)

    fun getNoFlightZones(): FeatureCollection =
        zoneRepository
            .findAll()
            .mapNotNull(NoFlightZone::toGeoFeature)
            .let(::FeatureCollection)


}

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


