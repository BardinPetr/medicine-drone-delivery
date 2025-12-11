package ru.bardinpetr.itmo.meddelivery.app.service.sim

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.service.drone.DroneService

@Service
class DBDroneFleet(
    val droneService: DroneService
) {
    @PostConstruct
    fun init() {

    }
}