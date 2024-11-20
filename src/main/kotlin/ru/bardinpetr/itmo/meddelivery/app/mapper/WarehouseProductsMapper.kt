package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.app.dto.WarehouseProductsDto
import ru.bardinpetr.itmo.meddelivery.app.entities.WarehouseProducts

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
    uses = [ProductTypeMapper::class, WarehouseMapper::class]
)
abstract class WarehouseProductsMapper {

    @Mappings(
        Mapping(source = "idProductId", target = "id.productId"),
        Mapping(source = "idWarehouseId", target = "id.warehouseId")
    )
    abstract fun toEntity(warehouseProductsDto: WarehouseProductsDto): WarehouseProducts

    @InheritInverseConfiguration(name = "toEntity")
    abstract fun toDto(warehouseProducts: WarehouseProducts): WarehouseProductsDto

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(
        warehouseProductsDto: WarehouseProductsDto,
        @MappingTarget warehouseProducts: WarehouseProducts
    ): WarehouseProducts
}