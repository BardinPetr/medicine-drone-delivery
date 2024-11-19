package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.app.dto.ProductTypeDto
import ru.bardinpetr.itmo.meddelivery.app.entities.ProductType

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class ProductTypeMapper {

    abstract fun toEntity(productTypeDto: ProductTypeDto): ProductType

    abstract fun toDto(productType: ProductType): ProductTypeDto

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(productTypeDto: ProductTypeDto, @MappingTarget productType: ProductType): ProductType
}