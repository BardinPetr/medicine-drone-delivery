package ru.bardinpetr.itmo.meddelivery.app.service.drone

import org.junit.jupiter.api.Test
import org.maplibre.spatialk.geojson.LineString
import org.maplibre.spatialk.geojson.Point
import org.maplibre.spatialk.geojson.toJson
import org.maplibre.spatialk.turf.measurement.distance
import org.maplibre.spatialk.turf.misc.nearestPointTo
import org.maplibre.spatialk.units.extensions.inMeters
import ru.bardinpetr.itmo.meddelivery.app.service.map.featureCollection
import ru.bardinpetr.itmo.meddelivery.app.service.map.toKPoint
import ru.bardinpetr.itmo.meddelivery.app.service.sim.DroneSimState
import ru.bardinpetr.itmo.meddelivery.app.service.sim.DroneSimulator

class DroneSimulatorTest {
    @Test
    fun testStep() {
        val epsilon = 100.0 // meters
        val speed = 10.0 // m/s
        val timeStep = 20.0 // sec

        val sim = DroneSimulator(0, speed, epsilon)

        val route = listOf(
            59.931796 to 30.316429,
            59.943835 to 30.291367,
            59.931452 to 30.265617,
            59.952604 to 30.217552,
            59.988856 to 30.296860,
            59.921302 to 30.438309,
            59.874466 to 30.337715,
            59.898581 to 30.289650,
            59.931968 to 30.362778,
            59.917000 to 30.385780,
            59.898064 to 30.344582,
            59.924399 to 30.289650,
        ).map { it.toKPoint() }
        sim.setRoute(route)
        sim.start(startRunner = false)

        val flightLog = mutableListOf<Point>()
        var reachedStartPoint = false
        while (true) {
            sim.step(timeStep)
            if (!reachedStartPoint && distance(sim.current, route[0]).inMeters < epsilon) {
                reachedStartPoint = true
            }
            if (reachedStartPoint) {
                flightLog.add(sim.current)
            }
            if (sim.currentState != DroneSimState.FLYING) {
                break
            }
        }

        val originalLine = LineString(route.map { it.coordinates })
        val worstDifferenceFromRoute =
            flightLog.maxOf {
                originalLine
                    .nearestPointTo(it.coordinates)
                    .properties.distance
                    .inMeters
            }

        println(flightLog.featureCollection.toJson())
        assert(worstDifferenceFromRoute < 2 * epsilon) { "Max deviation from the route: $worstDifferenceFromRoute m" }
    }
}
