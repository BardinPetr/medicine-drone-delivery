package ru.bardinpetr.itmo.meddelivery.app.service.drone.impl

import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.Point
import ru.bardinpetr.itmo.meddelivery.app.mapper.grpc.router.noFlyZonesRq
import ru.bardinpetr.itmo.meddelivery.app.mapper.grpc.router.planRouteRq
import ru.bardinpetr.itmo.meddelivery.app.mapper.grpc.router.toPointList
import ru.bardinpetr.itmo.meddelivery.app.repository.NoFlightZoneRepository
import ru.bardinpetr.itmo.meddelivery.app.service.drone.IRouterService
import ru.bardinpetr.itmo.meddelivery.app.service.router.RouterServiceGrpc
import java.util.concurrent.TimeUnit

@Service
class RouterServiceGrpc(
    private val zoneRep: NoFlightZoneRepository
) : IRouterService {

    @GrpcClient("router")
    private lateinit var routerGrpc: RouterServiceGrpc.RouterServiceBlockingStub

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    fun init() =
        zoneRep
            .findAll()
            .let(::noFlyZonesRq)
            .let(routerGrpc::updateNoFlightZones)

    override fun makeRoute(pt1: Point, pt2: Point): List<Point> =
        routerGrpc
            .planRoute(planRouteRq(pt1, pt2))
            .toPointList()
}
