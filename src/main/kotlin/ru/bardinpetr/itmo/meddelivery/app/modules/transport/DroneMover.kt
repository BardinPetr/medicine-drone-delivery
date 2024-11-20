import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.app.entities.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.DroneStatus
import ru.bardinpetr.itmo.meddelivery.app.entities.Point
import ru.bardinpetr.itmo.meddelivery.app.entities.RoutePoint
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.DroneRepository
import kotlin.math.*


@Service
class DroneMover(private val dronRep: DroneRepository) {

    fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371e3 // Earth radius in meters
        val phi1 = lat1.toRadians()
        val phi2 = lat2.toRadians()
        val deltaPhi = (lat2 - lat1).toRadians()
        val deltaLambda = (lon2 - lon1).toRadians()

        val a = sin(deltaPhi / 2).pow(2) + cos(phi1) * cos(phi2) * sin(deltaLambda / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return R * c // in meters
    }

    fun Double.toRadians() = this * PI / 180

    fun findClosestSegment(
        path: List<RoutePoint>,
        currentLat: Double,
        currentLon: Double
    ): Int {
        var closestSegmentIndex = -1
        var minDistance = Double.MAX_VALUE

        for (i in 0 until path.size - 1) {
            val currentPoint = path[i]
            val nextPoint = path[i + 1]

            val distanceToSegment = distanceToLineSegment(
                currentLat, currentLon,
                currentPoint.location.lat, currentPoint.location.lon,
                nextPoint.location.lat, nextPoint.location.lon
            )

            if (distanceToSegment < minDistance) {
                minDistance = distanceToSegment
                closestSegmentIndex = i
            }
        }
        return closestSegmentIndex
    }

    fun distanceToLineSegment(lat: Double, lon: Double, lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        // Calculate the distance from a point to a line segment
        val A = lat - lat1
        val B = lon - lon1
        val C = lat2 - lat1
        val D = lon2 - lon1

        val dot = A * C + B * D
        val lenSq = C * C + D * D
        val param = if (lenSq != 0.0) dot / lenSq else -1.0

        val xx: Double
        val yy: Double

        if (param < 0) {
            xx = lat1
            yy = lon1
        } else if (param > 1) {
            xx = lat2
            yy = lon2
        } else {
            xx = lat1 + param * C
            yy = lon1 + param * D
        }

        return haversineDistance(lat, lon, xx, yy)
    }

    fun findNextPoint(
        path: List<RoutePoint>,
        currentLat: Double,
        currentLon: Double,
        speed: Double, // speed in meters per second
        time: Double // time in seconds
    ): Point {
        val segmentIndex = findClosestSegment(path, currentLat, currentLon)
        if (segmentIndex == -1) return path.last().location // If not on a valid segment, return the last point

        var remainingDistance = speed * time
        var currentLatVar = currentLat
        var currentLonVar = currentLon

        for (i in segmentIndex until path.size - 1) {
            val currentPoint = path[i]
            val nextPoint = path[i + 1]

            val segmentDistance = haversineDistance(
                currentLatVar, currentLonVar,
                nextPoint.location.lat, nextPoint.location.lon
            )

            if (segmentDistance > remainingDistance) {
                // Compute the fraction of the segment to travel
                val fraction = remainingDistance / segmentDistance

                val newLat = currentLatVar + fraction * (nextPoint.location.lat - currentPoint.location.lat)
                val newLon = currentLonVar + fraction * (nextPoint.location.lon - currentPoint.location.lon)

                return Point(newLat, newLon)
            } else {
                remainingDistance -= segmentDistance
                currentLatVar = nextPoint.location.lat
                currentLonVar = nextPoint.location.lon
            }
        }

        // If we've completed the path return last point
        return path.last().location //TODO change status to flying_from
    }


    @Scheduled(fixedRate = 1500)
    @Transactional
    fun moveDrones() {

        var drones = dronRep.findAllByStatus(DroneStatus.FLYING_FROM)

        if (!drones.isEmpty()) {
            drones.forEach { drone ->
                run {
                    val route = drone.flightTask?.route
                    val path = route?.routePoints?.sortedByDescending { routePoint -> routePoint.id?.pointNumber }
                    val nextPoint = path?.let {
                        findNextPoint(
                            it,
                            drone.location.lat,
                            drone.location.lon,
                            drone.typeOfDrone.speed,
                            1.0
                        )
                    }

                    if (nextPoint != null) {
                        drone.location.lat = nextPoint.lat
                        drone.location.lon = nextPoint.lon

                        if (
                            (drone.location.lat - drone.flightTask!!.medicalFacility?.location?.lat!!) < 0.001 &&
                            (drone.location.lon - drone.flightTask!!.medicalFacility?.location?.lon!!) < 0.001 )
                            drone.status = DroneStatus.IDLE
                    }
                }
            }

            dronRep.saveAll(drones);
        }

//        System.out.println("Aaaa");
        drones = dronRep.findAllByStatus(DroneStatus.FLYING_TO)

        if (!drones.isEmpty()){
            drones.forEach { drone ->
                run {
                    val route = drone.flightTask?.route
                    val path = route?.routePoints?.sortedBy { point -> point.id?.pointNumber }
                    val nextPoint = path?.let {
                        findNextPoint(
                            it,
                            drone.location.lat,
                            drone.location.lon,
                            drone.typeOfDrone.speed,
                            1.0
                        )
                    }

                    if (nextPoint != null) {
                        drone.location.lat = nextPoint.lat
                        drone.location.lon = nextPoint.lon
                        if (
                            (drone.location.lat - drone.flightTask!!.medicalFacility?.location?.lat!!) < 0.001 &&
                            (drone.location.lon - drone.flightTask!!.medicalFacility?.location?.lon!!) < 0.001 )
                                drone.status = DroneStatus.FLYING_FROM
                    }
                }
            }

            dronRep.saveAll(drones);
        }


    }
}