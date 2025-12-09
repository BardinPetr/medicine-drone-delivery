package ru.bardinpetr.itmo.meddelivery.app.repository

import ru.bardinpetr.itmo.meddelivery.app.entities.geo.RoutePoint
import ru.bardinpetr.itmo.meddelivery.app.entities.geo.RoutePointId
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseCommonRestRepository

interface RoutePointRepository : IBaseCommonRestRepository<RoutePoint, RoutePointId>
        