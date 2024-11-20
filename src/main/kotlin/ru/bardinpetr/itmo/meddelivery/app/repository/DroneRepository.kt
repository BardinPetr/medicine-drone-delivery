
package ru.bardinpetr.itmo.meddelivery.common.auth.repository

import ru.bardinpetr.itmo.meddelivery.app.entities.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.DroneStatus
import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository

interface DroneRepository : ICommonRestRepository<Drone>{
    fun findAllByStatus(status :DroneStatus):List<Drone>;
}
        