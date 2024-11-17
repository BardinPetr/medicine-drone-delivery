package ru.bardinpetr.itmo.islab1.app.modules.map.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.islab1.common.handling.EnableResponseWrapper

//@RestController
//@RequestMapping("/api/map")
//@EnableResponseWrapper
//class MapController(
//    private val mapService: MapService
//) {
//
//    @GetMapping("products.geojson")
//    fun products(): String =
//        mapService.getProducts().json()
//
//    @GetMapping("persons.geojson")
//    fun persons(): String =
//        mapService.getPersons().json()
//}