package ru.bardinpetr.itmo.meddelivery.app.mapper

import org.mapstruct.*
import org.springframework.beans.factory.annotation.Autowired
import ru.bardinpetr.itmo.meddelivery.app.dto.RequestDto
import ru.bardinpetr.itmo.meddelivery.app.entities.Request
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.TaskStatus
import ru.bardinpetr.itmo.meddelivery.app.repository.MedicalFacilityRepository
import ru.bardinpetr.itmo.meddelivery.common.auth.service.UserService
import ru.bardinpetr.itmo.meddelivery.common.base.dto.IBaseMapper

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = [RequestEntryMapper::class]
)
abstract class RequestMapper : IBaseMapper<Request, RequestDto> {

    @Autowired
    private lateinit var requestEntryMapper: RequestEntryMapper

    @Autowired
    private lateinit var mfRepo: MedicalFacilityRepository

    @Autowired
    protected lateinit var userService: UserService

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

    @BeforeMapping
    fun init(@MappingTarget entity: Request, dto: RequestDto): Request {
        entity.user = userService.getCurrent()!!
        entity.medicalFacility = dto.medicalFacilityName?.let(mfRepo::getFirstByName)!!
        entity.status = TaskStatus.QUEUED
        entity.id = dto.id
        entity.requestEntries = dto.requestEntries.map(requestEntryMapper::toEntity).toMutableList()
        return entity
    }
}
