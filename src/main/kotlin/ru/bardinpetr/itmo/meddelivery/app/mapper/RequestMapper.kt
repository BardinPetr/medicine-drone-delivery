package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper
import ru.bardinpetr.itmo.meddelivery.app.dto.RequestDto
import ru.bardinpetr.itmo.meddelivery.app.entities.Request

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
    uses = [RequestEntryMapper::class]
)
abstract class RequestMapper : IBaseMapper<Request, RequestDto> {

    @Mappings(
        Mapping(source = "userUsername", target = "user.username"),
        Mapping(source = "medicalFacilityName", target = "medicalFacility.name")
    )
    abstract override fun toEntity(requestDto: RequestDto): Request

    @InheritInverseConfiguration(name = "toEntity")
    abstract override fun toDto(request: Request): RequestDto

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract override fun partialUpdate(requestDto: RequestDto, @MappingTarget request: Request): Request
}