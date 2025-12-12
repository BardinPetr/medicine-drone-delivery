package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.app.dto.WarehouseDto
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse
import ru.bardinpetr.itmo.meddelivery.common.base.dto.IBaseMapper

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
)
abstract class WarehouseMapper : IBaseMapper<Warehouse, WarehouseDto> {

    @Mappings(
        Mapping(source = "locationLat", target = "location.lat"),
        Mapping(source = "locationLon", target = "location.lon")
    )
    abstract override fun toEntity(warehouseDto: WarehouseDto): Warehouse

    @InheritInverseConfiguration(name = "toEntity")
    abstract override fun toDto(warehouse: Warehouse): WarehouseDto

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract override fun partialUpdate(warehouseDto: WarehouseDto, @MappingTarget warehouse: Warehouse): Warehouse
}
