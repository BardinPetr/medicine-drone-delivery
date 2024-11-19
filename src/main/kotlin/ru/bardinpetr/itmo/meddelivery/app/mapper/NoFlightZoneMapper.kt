package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.app.dto.NoFlightZoneDto
import ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class NoFlightZoneMapper {

    @Mappings(
        Mapping(source = "centerLat", target = "center.lat"),
        Mapping(source = "centerLon", target = "center.lon")
    )
    abstract fun toEntity(noFlightZoneDto: NoFlightZoneDto): NoFlightZone

    @InheritInverseConfiguration(name = "toEntity")
    abstract fun toDto(noFlightZone: NoFlightZone): NoFlightZoneDto

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(
        noFlightZoneDto: NoFlightZoneDto,
        @MappingTarget noFlightZone: NoFlightZone
    ): NoFlightZone
}