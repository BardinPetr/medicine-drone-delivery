package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import org.springframework.beans.factory.annotation.Autowired
import ru.bardinpetr.itmo.meddelivery.app.dto.DroneDto
import ru.bardinpetr.itmo.meddelivery.app.entities.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.FlightTask
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.FlightTaskRepository
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper
import kotlin.jvm.optionals.getOrNull

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
    uses = [TypeOfDroneMapper::class]
)
abstract class DroneMapper : IBaseMapper<Drone, DroneDto> {

    @Mappings(
        Mapping(source = "locationLat", target = "location.lat"),
        Mapping(source = "locationLon", target = "location.lon"),
    )
    override abstract fun toEntity(droneDto: DroneDto): Drone

    @InheritInverseConfiguration(name = "toEntity")
    override abstract fun toDto(drone: Drone): DroneDto

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    override abstract fun partialUpdate(droneDto: DroneDto, @MappingTarget drone: Drone): Drone
}