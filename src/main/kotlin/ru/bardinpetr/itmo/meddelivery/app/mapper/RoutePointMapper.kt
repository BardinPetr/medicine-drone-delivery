package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.app.dto.RoutePointDto
import ru.bardinpetr.itmo.meddelivery.app.entities.geo.RoutePoint

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class RoutePointMapper {

    @Mappings(
        Mapping(source = "idRouteId", target = "id.routeId"),
        Mapping(source = "idPointNumber", target = "id.pointNumber"),
        Mapping(source = "locationLat", target = "location.lat"),
        Mapping(source = "locationLon", target = "location.lon")
    )
    abstract fun toEntity(routePointDto: RoutePointDto): RoutePoint

    @InheritInverseConfiguration(name = "toEntity")
    abstract fun toDto(routePoint: RoutePoint): RoutePointDto

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(routePointDto: RoutePointDto, @MappingTarget routePoint: RoutePoint): RoutePoint
}
