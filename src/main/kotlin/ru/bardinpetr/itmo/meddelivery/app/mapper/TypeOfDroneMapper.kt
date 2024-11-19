package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper
import ru.bardinpetr.itmo.meddelivery.app.dto.TypeOfDroneDto
import ru.bardinpetr.itmo.meddelivery.app.entities.TypeOfDrone

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class TypeOfDroneMapper : IBaseMapper<TypeOfDrone, TypeOfDroneDto> {

    abstract override fun toEntity(typeOfDroneDto: TypeOfDroneDto): TypeOfDrone

    abstract override fun toDto(typeOfDrone: TypeOfDrone): TypeOfDroneDto

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract override fun partialUpdate(typeOfDroneDto: TypeOfDroneDto, @MappingTarget typeOfDrone: TypeOfDrone): TypeOfDrone
}