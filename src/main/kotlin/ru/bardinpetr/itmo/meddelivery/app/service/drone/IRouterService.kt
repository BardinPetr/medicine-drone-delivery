package ru.bardinpetr.itmo.meddelivery.app.service.drone

import ru.bardinpetr.itmo.meddelivery.app.entities.Point

interface IRouterService {
    fun makeRoute(pt1: Point, pt2: Point): List<Point>
}
