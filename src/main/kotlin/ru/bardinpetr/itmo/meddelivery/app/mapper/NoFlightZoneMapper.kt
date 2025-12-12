package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.app.dto.NoFlightZoneDto
import ru.bardinpetr.itmo.meddelivery.app.entities.NoFlightZone
import ru.bardinpetr.itmo.meddelivery.common.base.dto.IBaseMapper

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class NoFlightZoneMapper : IBaseMapper<NoFlightZone, NoFlightZoneDto> {

    @Mappings(
        Mapping(source = "centerLat", target = "center.lat"),
        Mapping(source = "centerLon", target = "center.lon")
    )
    abstract override fun toEntity(noFlightZoneDto: NoFlightZoneDto): NoFlightZone

    @InheritInverseConfiguration(name = "toEntity")
    abstract override fun toDto(noFlightZone: NoFlightZone): NoFlightZoneDto

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract override fun partialUpdate(
        noFlightZoneDto: NoFlightZoneDto,
        @MappingTarget noFlightZone: NoFlightZone
    ): NoFlightZone
}
