package ru.bardinpetr.itmo.meddelivery.app.modules.map.service


import io.github.dellisd.spatialk.geojson.FeatureCollection
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.geo.NoFlightZone
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
