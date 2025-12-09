package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.app.dto.ProductTypeDto
import ru.bardinpetr.itmo.meddelivery.app.entities.ProductType
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class ProductTypeMapper : IBaseMapper<ProductType, ProductTypeDto> {

    abstract override fun toEntity(productTypeDto: ProductTypeDto): ProductType

    abstract override fun toDto(productType: ProductType): ProductTypeDto

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract override fun partialUpdate(
        productTypeDto: ProductTypeDto,
        @MappingTarget productType: ProductType
    ): ProductType
}
