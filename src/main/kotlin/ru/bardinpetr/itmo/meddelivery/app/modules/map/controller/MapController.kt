package ru.bardinpetr.itmo.meddelivery.app.modules.map.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.modules.map.service.MapService
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

    @GetMapping("medicalFacility.geojson")
    fun medicalFacilitys(): String =
        mapService.getMedicalFacilities().json()

    @GetMapping("warehouses.geojson")
    fun warehouses(): String =
        mapService.getWarehouses().json()


    @GetMapping("noFlightZones.geojson")
    fun noZones(): String =
        mapService.getNoFlightZones().json()
}