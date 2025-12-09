
package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.geo.Route
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService

@Service
class RouteService : AbstractBaseService<Route>(Route::class)
