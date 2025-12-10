package ru.bardinpetr.itmo.meddelivery.app.service.drone

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone
import ru.bardinpetr.itmo.meddelivery.app.entities.Point
import ru.bardinpetr.itmo.meddelivery.app.repository.NoFlightZoneRepository
import java.util.concurrent.TimeUnit

@Suppress("all")
@Service
class RouterConnectorService(
    private val zoneRep: NoFlightZoneRepository,
    @Value("\${app.python-executable:}")
    private val pythonExecutable: String
) {
    // TODO make external service
    fun callRouter(zones: List<NoFlightZone>, start: Point, finish: Point): List<Point> {
        // Prepare input JSON
        val inputMap = mapOf(
            "circles" to zones.map { listOf(it.center.lat, it.center.lon, it.radius) },
            "p1" to listOf(start.lat, start.lon),
            "p2" to listOf(finish.lat, finish.lon)
        )

        val objectMapper = jacksonObjectMapper()
        val inputJson = objectMapper.writeValueAsString(inputMap)

        // Run the Python script
        val processBuilder = ProcessBuilder(pythonExecutable)
        val process = processBuilder.start()

        // Write input JSON to the process
        process.outputStream.bufferedWriter().use { it.write(inputJson) }

        process.waitFor(10, TimeUnit.SECONDS)
        if (process.exitValue() != 0) {
            throw RuntimeException("Failed to communicate with router process")
        }

        // Read the output JSON from the process
        val outputJson = process.inputStream.bufferedReader().use { it.readText() }

        // Deserialize the result into a list of Points
        val result: List<List<Double>> = objectMapper.readValue(outputJson)
        val points = result.mapIndexed { _, coordinates -> Point(coordinates[0], coordinates[1]) }
        return points.toMutableList()
    }

    fun makeRoute(pt1: Point, pt2: Point): List<Point> =
        callRouter(zoneRep.findAll(), pt1, pt2)
}
