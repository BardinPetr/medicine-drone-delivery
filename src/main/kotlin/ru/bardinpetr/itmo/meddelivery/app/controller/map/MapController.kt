package ru.bardinpetr.itmo.meddelivery.app.controller.map

import org.maplibre.spatialk.geojson.toJson
import org.springframework.web.bind.annotation.*
import ru.bardinpetr.itmo.meddelivery.app.service.map.MapService
import ru.bardinpetr.itmo.meddelivery.common.handling.EnableResponseWrapper
import ru.bardinpetr.itmo.meddelivery.common.models.IdType

@RestController
@RequestMapping("/api/map")
@EnableResponseWrapper
class MapController(
    private val mapService: MapService
) {

    @GetMapping("drones.geojson")
    fun drones(): String =
        mapService.getDrones().toJson()

    @PostMapping("drones.geojson")
    fun dronesPartial(@RequestBody ids: List<IdType>): String =
        mapService.getDrones(ids).toJson()

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
