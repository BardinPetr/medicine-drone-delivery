package ru.bardinpetr.itmo.meddelivery.app.repository

import ru.bardinpetr.itmo.meddelivery.app.entities.RoutePoint
import ru.bardinpetr.itmo.meddelivery.app.entities.RoutePointId
import ru.bardinpetr.itmo.meddelivery.common.base.repo.IBaseCommonRestRepository

interface RoutePointRepository : IBaseCommonRestRepository<RoutePoint, RoutePointId>
