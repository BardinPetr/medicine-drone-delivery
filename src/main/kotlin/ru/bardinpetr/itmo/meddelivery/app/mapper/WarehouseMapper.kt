package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.app.dto.WarehouseDto
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
    uses = [WarehouseProductsMapper::class]
)
abstract class WarehouseMapper {

    @Mappings(
        Mapping(source = "locationLat", target = "location.lat"),
        Mapping(source = "locationLon", target = "location.lon")
    )
    abstract fun toEntity(warehouseDto: WarehouseDto): Warehouse

    @InheritInverseConfiguration(name = "toEntity")
    abstract fun toDto(warehouse: Warehouse): WarehouseDto

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(warehouseDto: WarehouseDto, @MappingTarget warehouse: Warehouse): Warehouse
}