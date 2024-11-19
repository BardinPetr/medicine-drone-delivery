package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper
import ru.bardinpetr.itmo.meddelivery.app.dto.MedicalFacilityDto
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class MedicalFacilityMapper : IBaseMapper<MedicalFacility, MedicalFacilityDto> {

    @Mappings(
        Mapping(source = "responsibleUserUsername", target = "responsibleUser.username"),
        Mapping(source = "locationLat", target = "location.lat"),
        Mapping(source = "locationLon", target = "location.lon")
    )
    abstract override fun toEntity(medicalFacilityDto: MedicalFacilityDto): MedicalFacility

    @InheritInverseConfiguration(name = "toEntity")
    abstract override fun toDto(medicalFacility: MedicalFacility): MedicalFacilityDto

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract override fun partialUpdate(medicalFacilityDto: MedicalFacilityDto, @MappingTarget medicalFacility: MedicalFacility): MedicalFacility
}