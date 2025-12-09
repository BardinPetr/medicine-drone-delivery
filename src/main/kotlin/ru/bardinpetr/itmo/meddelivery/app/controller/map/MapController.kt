package ru.bardinpetr.itmo.meddelivery.app.controller.map

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
        mapService.getDrones().json()

    @GetMapping("medical.geojson")
    fun medicalFacilities(): String =
        mapService.getMedicalFacilities().json()

    @GetMapping("warehouses.geojson")
    fun warehouses(): String =
        mapService.getWarehouses().json()

    @GetMapping("noFlightZones.geojson")
    fun noZones(): String =
        mapService.getNoFlightZones().json()
}
