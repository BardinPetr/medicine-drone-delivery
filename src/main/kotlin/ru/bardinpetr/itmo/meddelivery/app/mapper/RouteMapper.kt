package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper
import org.springframework.beans.factory.annotation.Autowired
import ru.bardinpetr.itmo.meddelivery.app.dto.RouteDto
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.app.entities.Route
import ru.bardinpetr.itmo.meddelivery.app.entities.Warehouse
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.MedicalFacilityRepository
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.RequestRepository
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.RouteRepository
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.WarehouseRepository
import kotlin.jvm.optionals.getOrNull

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
    uses = [RoutePointMapper::class]
)
abstract class RouteMapper : IBaseMapper<Route, RouteDto> {

    @Autowired
    private lateinit var mRepo: MedicalFacilityRepository

    @Autowired
    private lateinit var wRepo: WarehouseRepository

    @Mappings(
        Mapping(source = "warehouseId", target = "warehouse.id"),
        Mapping(source = "medicalFacilityId", target = "medicalFacility.id")
    )
    abstract override fun toEntity(routeDto: RouteDto): Route

    @InheritInverseConfiguration(name = "toEntity")
    abstract override fun toDto(route: Route): RouteDto

    @Mappings(
        Mapping(source = "warehouseId", target = "warehouse"),
        Mapping(source = "medicalFacilityId", target = "medicalFacility")
    )
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract override fun partialUpdate(routeDto: RouteDto, @MappingTarget route: Route): Route

    fun createWarehouse(warehouseId: Long?): Warehouse? =
        warehouseId
            ?.let(wRepo::findById)
            ?.getOrNull()

    fun createMedicalFacility(medicalFacilityId: Long?): MedicalFacility? =
        medicalFacilityId
            ?.let(mRepo::findById)
            ?.getOrNull()
}