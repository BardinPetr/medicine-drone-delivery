package ru.bardinpetr.itmo.meddelivery.app.service.map

import org.maplibre.spatialk.geojson.FeatureCollection
import org.maplibre.spatialk.geojson.Point
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse
import ru.bardinpetr.itmo.meddelivery.app.repository.DroneRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.MedicalFacilityRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.NoFlightZoneRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.WarehouseRepository

@Service
class MapService(
    private val droneRepository: DroneRepository,
    private val medicalFacilityRepository: MedicalFacilityRepository,
    private val warehouseRepository: WarehouseRepository,
    private val zoneRepository: NoFlightZoneRepository,
) {
    fun getDrones(): FeatureCollection<Point, *> =
        droneRepository
            .findAll()
            .mapNotNull(Drone::toGeoFeature)
            .let(::FeatureCollection)

    fun getMedicalFacilities(): FeatureCollection<Point, *> =
        medicalFacilityRepository
            .findAll()
            .mapNotNull(MedicalFacility::toGeoFeature)
            .let(::FeatureCollection)

    fun getWarehouses(): FeatureCollection<Point, *> =
        warehouseRepository
            .findAll()
            .mapNotNull(Warehouse::toGeoFeature)
            .let(::FeatureCollection)

    fun getNoFlightZones(): FeatureCollection<Point, *> =
        zoneRepository
            .findAll()
            .mapNotNull(NoFlightZone::toGeoFeature)
            .let(::FeatureCollection)
}
