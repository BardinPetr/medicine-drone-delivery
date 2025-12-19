package ru.bardinpetr.itmo.meddelivery.app.service.fleet

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.FlightTask
import ru.bardinpetr.itmo.meddelivery.app.entities.Point
import ru.bardinpetr.itmo.meddelivery.app.entities.RoutePoint
import ru.bardinpetr.itmo.meddelivery.app.mapper.ePoint
import ru.bardinpetr.itmo.meddelivery.app.mapper.skPoint
import ru.bardinpetr.itmo.meddelivery.app.service.drone.DroneService
import ru.bardinpetr.itmo.meddelivery.app.service.sim.DBDroneSimulator
import ru.bardinpetr.itmo.meddelivery.common.utils.logger
import java.util.concurrent.ScheduledThreadPoolExecutor

@Service
class DBDroneFleet(
    val droneService: DroneService,
    @Value("\${app.sim.threads}")
    val threadPoolSize: Int = 8,
    @Value("\${app.sim.epsilon-meters}")
    private val pointReachEpsilonMeters: Double,
    @Value("\${app.sim.time-period-millis}")
    private val stepPeriodMillis: Long,
) : IDroneFleet {
    private val log = logger<DBDroneFleet>()
    private val pool = ScheduledThreadPoolExecutor(threadPoolSize)
    private var fleet: Map<Long, DBDroneSimulator> = emptyMap()

    override fun makeFleet() {
        fleet = droneService
            .getAll()
            .associateBy({ it.id!! }) {
                DBDroneSimulator(
                    droneService,
                    pool,
                    it.id!!,
                    it.typeOfDrone.speed,
                    pointReachEpsilonMeters,
                    stepPeriodMillis
                )
            }
    }

    override fun launch(task: FlightTask) {
        val drone = fleet[task.drones.first().id!!]!!
        val route = task
            .route!!
            .routePoints
            .map(RoutePoint::location)
            .map(Point::skPoint)

        log.info("[FLEET DRONE=${drone.droneId}] Started with route ${route.first().ePoint} -> ${route.last().ePoint}")
        drone.setRoute(route)
        drone.start()
    }
}
