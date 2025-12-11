package ru.bardinpetr.itmo.meddelivery.app.service.sim

import ru.bardinpetr.itmo.meddelivery.common.models.IdType

class DBDroneSimulator(droneId: IdType, speedMS: Double, pointReachEpsilonMeters: Double) : BaseDroneSimulator(
    droneId, speedMS,
    pointReachEpsilonMeters
)