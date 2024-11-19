package ru.bardinpetr.itmo.meddelivery.app.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper
import ru.bardinpetr.itmo.meddelivery.app.dto.FlightTaskDto
import ru.bardinpetr.itmo.meddelivery.app.entities.FlightTask
import ru.bardinpetr.itmo.meddelivery.app.entities.Request
import ru.bardinpetr.itmo.meddelivery.app.entities.Route
import org.springframework.beans.factory.annotation.Autowired
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.RequestRepository
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.RouteRepository
import kotlin.jvm.optionals.getOrNull

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class FlightTaskMapper : IBaseMapper<FlightTask, FlightTaskDto> {

    @Autowired
    private lateinit var reqRepo: RequestRepository

    @Autowired
    private lateinit var routeRepo: RouteRepository

    @Mappings(
        Mapping(source = "requestId", target = "request.id"),
        Mapping(source = "productTypeProductTypeName", target = "productType.type"),
        Mapping(source = "warehouseWarehouseName", target = "warehouse.name"),
        Mapping(source = "medicalFacilityMedicalFacilityName", target = "medicalFacility.name"),
        Mapping(source = "routeId", target = "route.id")
    )
    abstract override fun toEntity(flightTaskDto: FlightTaskDto): FlightTask

    @InheritInverseConfiguration(name = "toEntity")
    abstract override fun toDto(flightTask: FlightTask): FlightTaskDto

    @InheritConfiguration(name = "toEntity")
    @Mappings(
        Mapping(source = "requestId", target = "request"),
        Mapping(source = "routeId", target = "route")
    )
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract override fun partialUpdate(flightTaskDto: FlightTaskDto, @MappingTarget flightTask: FlightTask): FlightTask

    fun createRequest(requestId: Long?): Request? =
        requestId
            ?.let(reqRepo::findById)
            ?.getOrNull()

    fun createRoute(routeId: Long?): Route? =
        routeId
            ?.let(routeRepo::findById)
            ?.getOrNull()
}