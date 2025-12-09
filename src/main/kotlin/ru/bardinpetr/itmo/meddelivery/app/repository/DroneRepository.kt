package ru.bardinpetr.itmo.meddelivery.app.repository

import ru.bardinpetr.itmo.meddelivery.app.entities.drones.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.DroneStatus
import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository

interface DroneRepository : ICommonRestRepository<Drone> {
    fun findAllByStatus(status: DroneStatus): List<Drone>
}
