package ru.bardinpetr.itmo.meddelivery.app.modules.transport
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.app.entities.DroneStatus
import ru.bardinpetr.itmo.meddelivery.app.entities.Point
import ru.bardinpetr.itmo.meddelivery.app.entities.TaskStatus
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.DroneRepository
import java.lang.Math.pow
import kotlin.math.*


@Service
class DroneMover(private val dronRep: DroneRepository) {

    fun findClosestPoint(
        path: List<Point>,
        currentLat: Double,
        currentLon: Double
    ): Int {
        var closestPointIndex = -1
        var minDistance = Double.MAX_VALUE

        for (i in path.size - 1 downTo 0) {
            val distanceToPoint = calculateDistance(
                path[i], Point(currentLat, currentLon)
            )

            if (distanceToPoint < minDistance) {
                minDistance = distanceToPoint
                closestPointIndex = i
            }
        }
        return closestPointIndex
    }



    fun findNextPoint(
        path: List<Point>,
        currentLat: Double,
        currentLon: Double,
        speed: Double, // speed in meters per second
        time: Double // time in seconds
    ): Point {
        val pointId = findClosestPoint(path, currentLat, currentLon)

        return path[min(pointId + 1, path.size - 1)]
    }

    fun calculateDistance(point1: Point, point2: Point): Double {
        return Math.sqrt(pow(point2.lat - point1.lat, 2.0) + pow(point2.lon - point1.lon, 2.0))
    }

    fun addPointsAtInterval(forwardPath: List<Point>, interval: Double): List<Point> {
        val result = mutableListOf<Point>()
        if (forwardPath.isEmpty()) return result

        result.add(forwardPath[0])
        for (i in 0 until forwardPath.size - 1) {
            val currentPoint = forwardPath[i]
            val nextPoint = forwardPath[i + 1]
            val distance = calculateDistance(currentPoint, nextPoint)
            val numberOfNewPoints = (distance / interval).toInt()

            for (j in 1..numberOfNewPoints) {
                val newX = currentPoint.lat + (nextPoint.lat - currentPoint.lat) * (j * interval / distance)
                val newY = currentPoint.lon + (nextPoint.lon - currentPoint.lon) * (j * interval / distance)
                result.add(Point(newX, newY))
            }
            result.add(nextPoint)
        }
        return result
    }


    @Scheduled(fixedRate = 250)
    @Transactional
    fun moveDrones() {
        val time = 1
        var drones = dronRep.findAllByStatus(DroneStatus.FLYING_FROM)

        if (!drones.isEmpty()) {
            drones.forEach { drone ->
                run {
                    val route = drone.flightTask?.route
                    val path = route?.routePoints?.sortedByDescending { routePoint -> routePoint.id?.pointNumber }
                    if (path != null) {
                        val ppath = addPointsAtInterval(
                            path.map { routePoint -> routePoint.location },
                            drone.typeOfDrone.speed * time
                        )
                        val nextPoint = ppath.let {
                            findNextPoint(
                                it,
                                drone.location.lat,
                                drone.location.lon,
                                drone.typeOfDrone.speed,
                                time.toDouble()
                            )
                        }

                        drone.location.lat = nextPoint.lat
                        drone.location.lon = nextPoint.lon

                        if (
                            abs(drone.location.lat - drone.flightTask!!.warehouse?.location?.lat!!) < 0.0001 &&
                            abs(drone.location.lon - drone.flightTask!!.warehouse?.location?.lon!!) < 0.0001
                        ) {
                            drone.status = DroneStatus.IDLE
                            drone.flightTask!!.status = TaskStatus.COMPLETED
                        }
                    }

                }
            }

            dronRep.saveAllAndFlush(drones)
        }
//        System.out.println("Aaaa")
        drones = dronRep.findAllByStatus(DroneStatus.FLYING_TO)

        if (!drones.isEmpty()) {
            drones.forEach { drone ->
                run {
                    val route = drone.flightTask?.route
                    val path = route?.routePoints?.sortedBy { routePoint -> routePoint.id?.pointNumber }
                    if (path != null) {
                        val ppath = addPointsAtInterval(
                            path.map { routePoint -> routePoint.location },
                            drone.typeOfDrone.speed * time
                        )
                        val nextPoint = ppath.let {
                            findNextPoint(
                                it,
                                drone.location.lat,
                                drone.location.lon,
                                drone.typeOfDrone.speed,
                                time.toDouble()
                            )
                        }

                        drone.location.lat = nextPoint.lat
                        drone.location.lon = nextPoint.lon

                        drone.location.lat = nextPoint.lat
                        drone.location.lon = nextPoint.lon
                        if (
                            abs(drone.location.lat - drone.flightTask!!.medicalFacility?.location?.lat!!) < 0.0001 &&
                            abs(drone.location.lon - drone.flightTask!!.medicalFacility?.location?.lon!!) < 0.0001
                        )
                            drone.status = DroneStatus.FLYING_FROM
                    }


                }
            }

            dronRep.saveAllAndFlush(drones)
        }


    }
}