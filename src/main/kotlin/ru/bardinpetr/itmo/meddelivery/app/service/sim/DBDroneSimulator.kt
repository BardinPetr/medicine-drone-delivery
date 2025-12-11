package ru.bardinpetr.itmo.meddelivery.app.service.sim

import ru.bardinpetr.itmo.meddelivery.app.entities.enums.DroneStatus
import ru.bardinpetr.itmo.meddelivery.app.mapper.ePoint
import ru.bardinpetr.itmo.meddelivery.app.service.drone.DroneService
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import java.util.concurrent.ScheduledThreadPoolExecutor
import kotlin.math.ceil

class DBDroneSimulator(
    private val droneService: DroneService,
    pool: ScheduledThreadPoolExecutor?,
    droneId: IdType,
    speedMS: Double,
    pointReachEpsilonMeters: Double,
    stepPeriodMillis: Long,
) : BaseDroneSimulator(pool, droneId, speedMS, pointReachEpsilonMeters, stepPeriodMillis) {

    override fun step(timeDeltaS: Double) {
        super.step(timeDeltaS)
        droneService.updatePosition(droneId, current.ePoint)
    }

    override fun onPointReached(reachedId: Int) {
        super.onPointReached(reachedId)
        if (reachedId == ceil(route.size / 2.0).toInt()) {
            droneService.updateDroneState(droneId, DroneStatus.FLYING_FROM)
        }
    }

    override fun onRouteEnded() {
        super.onRouteEnded()
        droneService.droneArrived(droneId)
    }
}
