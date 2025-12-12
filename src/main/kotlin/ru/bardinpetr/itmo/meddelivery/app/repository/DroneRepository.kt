package ru.bardinpetr.itmo.meddelivery.app.repository

import ru.bardinpetr.itmo.meddelivery.app.entities.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.DroneStatus
import ru.bardinpetr.itmo.meddelivery.common.base.repo.ICommonRestRepository

interface DroneRepository : ICommonRestRepository<Drone> {
    fun findAllByStatus(status: DroneStatus): List<Drone>
}
