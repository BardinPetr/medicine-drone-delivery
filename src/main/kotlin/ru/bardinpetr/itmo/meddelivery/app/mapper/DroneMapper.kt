package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.app.dto.DroneDto
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.Drone
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = [TypeOfDroneMapper::class]
)
abstract class DroneMapper : IBaseMapper<Drone, DroneDto> {

    @Mappings(
        Mapping(source = "locationLat", target = "location.lat"),
        Mapping(source = "locationLon", target = "location.lon"),
    )
    abstract override fun toEntity(droneDto: DroneDto): Drone

    @InheritInverseConfiguration(name = "toEntity")
    abstract override fun toDto(drone: Drone): DroneDto

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract override fun partialUpdate(droneDto: DroneDto, @MappingTarget drone: Drone): Drone
}
