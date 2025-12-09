package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.app.dto.FlightTaskDto
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.FlightTask
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = [
        RequestMapper::class,
        ProductTypeMapper::class,
        WarehouseMapper::class,
        MedicalFacilityMapper::class,
        RouteMapper::class
    ]
)
abstract class FlightTaskMapper : IBaseMapper<FlightTask, FlightTaskDto> {

    abstract override fun toEntity(flightTaskDto: FlightTaskDto): FlightTask

    abstract override fun toDto(flightTask: FlightTask): FlightTaskDto

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract override fun partialUpdate(flightTaskDto: FlightTaskDto, @MappingTarget flightTask: FlightTask): FlightTask
}
