package ru.bardinpetr.itmo.meddelivery.app.service.fleet

import ru.bardinpetr.itmo.meddelivery.app.entities.FlightTask

interface IDroneFleet {
    fun makeFleet()
    fun launch(task: FlightTask)
}
