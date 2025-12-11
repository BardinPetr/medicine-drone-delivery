package ru.bardinpetr.itmo.meddelivery.app.controller.map

import org.maplibre.spatialk.geojson.toJson
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.service.map.MapService
import ru.bardinpetr.itmo.meddelivery.common.handling.EnableResponseWrapper

@RestController
@RequestMapping("/api/map")
@EnableResponseWrapper
class MapController(
    private val mapService: MapService
) {

    @GetMapping("drones.geojson")
    fun drones(): String =
        mapService.getDrones().toJson()

    @GetMapping("medical.geojson")
    fun medicalFacilities(): String =
        mapService.getMedicalFacilities().toJson()

    @GetMapping("warehouses.geojson")
    fun warehouses(): String =
        mapService.getWarehouses().toJson()

    @GetMapping("noFlightZones.geojson")
    fun noZones(): String =
        mapService.getNoFlightZones().toJson()
}
