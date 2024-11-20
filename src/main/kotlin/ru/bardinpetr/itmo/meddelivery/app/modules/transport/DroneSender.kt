package ru.bardinpetr.itmo.meddelivery.app.modules.transport

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.*
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.DroneRepository
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.FlightTaskRepository
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.NoFlightZoneRepository
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.RouteRepository

@Service
class DroneSender(
    private val zoneRep: NoFlightZoneRepository,
    private val routeRepository: RouteRepository,
    private val flaskRepository: FlightTaskRepository,
    private val droneRepository: DroneRepository
) {
    fun createRoute(zones: List<NoFlightZone>, start: Point, finish: Point, route: Route): MutableList<RoutePoint>{
        // Prepare input JSON
        val inputMap = mapOf(
            "circles" to zones.map { listOf(it.center.lat, it.center.lon, it.radius) },
            "p1" to listOf(start.lat, start.lon),
            "p2" to listOf(finish.lat, finish.lon)
        )

        val objectMapper = jacksonObjectMapper()
        val inputJson = objectMapper.writeValueAsString(inputMap)

        // Run the Python script
        val processBuilder = ProcessBuilder("python", "router.py")
        val process = processBuilder.start()

        // Write input JSON to the process
        process.outputStream.bufferedWriter().use { it.write(inputJson) }

        // Read the output JSON from the process
        val outputJson = process.inputStream.bufferedReader().use { it.readText() }

        // Deserialize the result into a list of Points
        val result: List<List<Double>> = objectMapper.readValue(outputJson)
        val points = result.mapIndexed { index, coordinates ->
            RoutePoint(
                id = null,
                route = route,
                location = Point(coordinates[0], coordinates[1])
            )
        }
        return points.toMutableList();
    }

    fun sendDrone(drone: Drone){
        val zones = zoneRep.findAll()
        val start = drone.flightTask?.warehouse?.location
        val finish = drone.flightTask?.medicalFacility?.location

        val route = drone.flightTask?.warehouse?.let {
            drone.flightTask!!.medicalFacility?.let { it1 ->
                Route(
                    it,
                    it1,
                )
            }
        }

        if (start != null && finish != null && route != null){
            val points = createRoute(zones, start, finish, route)
            routeRepository.save(route)
            points.mapIndexed() {index, it -> it.id = route.id?.let { it1 -> RoutePointId(it1, index) } }
            route.routePoints.addAll(points)
            routeRepository.save(route)

            drone.flightTask!!.route = route
            flaskRepository.save(drone.flightTask!!)

            drone.location = start
            drone.status = DroneStatus.FLYING_TO

            droneRepository.save(drone)

        }

    }
}