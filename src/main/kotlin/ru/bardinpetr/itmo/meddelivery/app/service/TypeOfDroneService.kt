
package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.TypeOfDrone
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService

@Service
class TypeOfDroneService : AbstractBaseService<TypeOfDrone>(TypeOfDrone::class)
