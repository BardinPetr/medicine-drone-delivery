package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import org.springframework.beans.factory.annotation.Autowired
import ru.bardinpetr.itmo.meddelivery.app.dto.RequestEntryDto
import ru.bardinpetr.itmo.meddelivery.app.entities.product.Request
import ru.bardinpetr.itmo.meddelivery.app.entities.product.RequestEntry
import ru.bardinpetr.itmo.meddelivery.app.repository.RequestRepository
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper
import kotlin.jvm.optionals.getOrNull

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class RequestEntryMapper : IBaseMapper<RequestEntry, RequestEntryDto> {

    @Autowired
    private lateinit var reqRepo: RequestRepository

    @Mappings(
        Mapping(source = "requestId", target = "request.id"),
        Mapping(source = "productTypeType", target = "productType.type")
    )
    abstract override fun toEntity(requestEntryDto: RequestEntryDto): RequestEntry

    @InheritInverseConfiguration(name = "toEntity")
    abstract override fun toDto(requestEntry: RequestEntry): RequestEntryDto

    @Mappings(
        Mapping(source = "requestId", target = "request"),
        Mapping(source = "productTypeType", target = "productType.type")
    )
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract override fun partialUpdate(
        requestEntryDto: RequestEntryDto,
        @MappingTarget requestEntry: RequestEntry
    ): RequestEntry

    fun createRequest(requestId: Long?): Request? =
        requestId
            ?.let(reqRepo::findById)
            ?.getOrNull()
}