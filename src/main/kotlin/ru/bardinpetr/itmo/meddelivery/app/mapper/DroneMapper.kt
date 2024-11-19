package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper
import org.springframework.beans.factory.annotation.Autowired
import ru.bardinpetr.itmo.meddelivery.app.dto.DroneDto
import ru.bardinpetr.itmo.meddelivery.app.entities.Drone
import ru.bardinpetr.itmo.meddelivery.app.entities.FlightTask
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.FlightTaskRepository
import kotlin.jvm.optionals.getOrNull

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class DroneMapper : IBaseMapper<Drone, DroneDto> {

    @Autowired
    private lateinit var ftRepo: FlightTaskRepository

    @Mappings(
        Mapping(source = "typeOfDroneName", target = "typeOfDrone.name"),
        Mapping(source = "locationLat", target = "location.lat"),
        Mapping(source = "locationLon", target = "location.lon"),
        Mapping(source = "flightTaskId", target = "flightTask.id")
    )
    abstract override fun toEntity(droneDto: DroneDto): Drone

    @InheritInverseConfiguration(name = "toEntity")
    abstract override fun toDto(drone: Drone): DroneDto

    @InheritConfiguration(name = "toEntity")
    @Mapping(source = "flightTaskId", target = "flightTask")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract override fun partialUpdate(droneDto: DroneDto, @MappingTarget drone: Drone): Drone

    fun createFlightTask(flightTaskId: Long?): FlightTask? =
        flightTaskId
            ?.let(ftRepo::findById)
            ?.getOrNull()
}