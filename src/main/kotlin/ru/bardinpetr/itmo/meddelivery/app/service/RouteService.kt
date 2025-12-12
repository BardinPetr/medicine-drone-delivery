package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.Route
import ru.bardinpetr.itmo.meddelivery.app.entities.RoutePoint
import ru.bardinpetr.itmo.meddelivery.app.entities.RoutePointId
import ru.bardinpetr.itmo.meddelivery.app.repository.RouteRepository
import ru.bardinpetr.itmo.meddelivery.app.service.drone.RouterConnectorService
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.utils.error.notFound

@Service
class RouteService(
    override val repo: RouteRepository,
    private val router: RouterConnectorService,
    private val warehouseService: WarehouseService,
    private val medicalService: MedicalFacilityService,
) : AbstractBaseService<Route>(Route::class, repo) {

    fun findOrCreateRoute(warehouseId: IdType, medicalId: IdType): Route {
        var route = repo.findByWarehouseIdAndMedicalFacilityId(warehouseId, medicalId)
        if (route == null) {
            val warehouseStart = warehouseService.get(warehouseId) ?: notFound()
            val medicalEnd = medicalService.get(medicalId) ?: notFound()
            route = repo.save(
                Route(
                    warehouse = warehouseStart,
                    medicalFacility = medicalEnd
                )
            )
            router
                .makeRoute(warehouseStart.location, medicalEnd.location)
                .let { it + it.reversed().drop(1) }
                .mapIndexed { idx, pt ->
                    RoutePoint(
                        id = RoutePointId(route.id!!, idx),
                        route = route,
                        location = pt,
                    )
                }
                .let { route.routePoints.addAll(it) }
            return repo.save(route)
        }
        return route
    }
}
