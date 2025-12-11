@file:OptIn(ExperimentalAtomicApi::class)

package ru.bardinpetr.itmo.meddelivery.app.service.sim

import org.maplibre.spatialk.geojson.Point
import org.maplibre.spatialk.turf.measurement.bearingTo
import org.maplibre.spatialk.turf.measurement.distance
import org.maplibre.spatialk.turf.measurement.offset
import org.maplibre.spatialk.units.extensions.inMeters
import org.maplibre.spatialk.units.extensions.meters
import org.springframework.beans.factory.annotation.Value
import ru.bardinpetr.itmo.meddelivery.app.service.map.pt
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.utils.logger
import kotlin.concurrent.atomics.AtomicReference
import kotlin.concurrent.atomics.ExperimentalAtomicApi

open class BaseDroneSimulator(
    val droneId: IdType,
    val speedMS: Double,
    @Value("\${app.sim.epsilon-meters}")
    private val pointReachEpsilonMeters: Double,
) {
    private val log = logger<BaseDroneSimulator>("ID#$droneId")

    protected open var position: Point = Point(30.21038, 59.95141)
    protected open var state: AtomicReference<DroneSimState> = AtomicReference(DroneSimState.IDLE)

    // last reached point number
    protected open var currentPoint = 0
    protected open val route: MutableList<Point> = mutableListOf()

    fun setRoute(new: List<Point>) {
        require(new.size >= 2)
        currentPoint = -1
        route.clear()
        route.addAll(new)
        state.store(DroneSimState.IDLE)
    }

    fun start(startRunner: Boolean = true) {
        state.store(DroneSimState.FLYING)
    }

    val nextPointId: Int?
        get() = when {
            state.load() != DroneSimState.FLYING -> null
            currentPoint == (route.size - 1) -> null
            else -> currentPoint + 1
        }

    val nextPoint: Point?
        get() = nextPointId?.let { route[it] }

    /**
     * @returns next point & index of reached point of path if it was
     */
    open fun step(timeDeltaS: Double) {
        val nextId = nextPointId ?: return
        val next = route[nextId]
        val delta = distance(position, next)
        if (delta.inMeters < pointReachEpsilonMeters) {
            onPointReached(nextId)
            return
        }
        val heading = position.bearingTo(next)
        val step = (timeDeltaS * speedMS).meters
        log.debug("ID=$nextId dist=${delta.inMeters} | h=$heading s=$step")
        position = position.offset(step, heading).pt
    }

    open fun onPointReached(reachedId: Int) {
        log.info("Point reached #$reachedId/${route.size - 1}")
        if (reachedId == (route.size - 1)) {
            onRouteEnded()
        } else {
            currentPoint += 1
        }
    }

    open fun onRouteEnded() {
        log.info("Route ended")
        state.store(DroneSimState.REACHED)
    }

    val current
        get() = position
    val currentState
        get() = state.load()
}

//    @Scheduled(fixedRate = 250)
//    @Transactional
//    fun moveDrones() {
//        var drones = repo.findAllByStatus(DroneStatus.FLYING_FROM)
//
//        if (!drones.isEmpty()) {
//            drones.forEach { drone ->
//                run {
//                    val route = drone.flightTask?.route
//                    val path = route?.routePoints?.sortedByDescending { routePoint -> routePoint.id?.pointNumber }
//                    if (path != null) {
//                        val ppath = addPointsAtInterval(
//                            path.map { routePoint -> routePoint.location },
//                            drone.typeOfDrone.speed * time
//                        )
//                        val nextPoint = ppath.let {
//                            findNextPoint(
//                                it,
//                                drone.location.lat,
//                                drone.location.lon,
//                                drone.typeOfDrone.speed,
//                                time.toDouble()
//                            )
//                        }
//
//                        drone.location.lat = nextPoint.lat
//                        drone.location.lon = nextPoint.lon
//
//                        if (
//                            abs(drone.location.lat - drone.flightTask!!.warehouse?.location?.lat!!) < 0.0001 &&
//                            abs(drone.location.lon - drone.flightTask!!.warehouse?.location?.lon!!) < 0.0001
//                        ) {
//                            drone.status = DroneStatus.IDLE
//                            drone.flightTask!!.status = TaskStatus.COMPLETED
//                            droneService.droneArrived(drone)
//                        }
//                    }
//                }
//            }
//
//            repo.saveAllAndFlush(drones)
//        }
//        drones = repo.findAllByStatus(DroneStatus.FLYING_TO)
//
//        if (!drones.isEmpty()) {
//            drones.forEach { drone ->
//                run {
//                    val route = drone.flightTask?.route
//                    val path = route?.routePoints?.sortedBy { routePoint -> routePoint.id?.pointNumber }
//                    if (path != null) {
//                        val ppath = addPointsAtInterval(
//                            path.map { routePoint -> routePoint.location },
//                            drone.typeOfDrone.speed * time
//                        )
//                        val nextPoint = ppath.let {
//                            findNextPoint(
//                                it,
//                                drone.location.lat,
//                                drone.location.lon,
//                                drone.typeOfDrone.speed,
//                                time.toDouble()
//                            )
//                        }
//
//                        drone.location.lat = nextPoint.lat
//                        drone.location.lon = nextPoint.lon
//
//                        drone.location.lat = nextPoint.lat
//                        drone.location.lon = nextPoint.lon
//                        if (
//                            abs(drone.location.lat - drone.flightTask!!.medicalFacility?.location?.lat!!) < 0.0001 &&
//                            abs(drone.location.lon - drone.flightTask!!.medicalFacility?.location?.lon!!) < 0.0001
//                        ) {
//                            drone.status = DroneStatus.FLYING_FROM
//                        }
//                    }
//                }
//            }
//
//            repo.saveAllAndFlush(drones)
//        }
//    }
