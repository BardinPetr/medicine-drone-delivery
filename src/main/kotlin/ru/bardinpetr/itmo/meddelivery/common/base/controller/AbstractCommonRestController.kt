package ru.bardinpetr.itmo.meddelivery.common.base.controller

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.bardinpetr.itmo.meddelivery.common.auth.service.UserService
import ru.bardinpetr.itmo.meddelivery.common.base.dto.IBaseDto
import ru.bardinpetr.itmo.meddelivery.common.base.dto.IBaseMapper
import ru.bardinpetr.itmo.meddelivery.common.base.repo.ICommonRestRepository
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.handling.EnableResponseWrapper
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.search.FilterModel
import ru.bardinpetr.itmo.meddelivery.common.utils.error.NotFoundException
import ru.bardinpetr.itmo.meddelivery.common.ws.WebSocketNotifyService
import kotlin.reflect.KClass

@Validated
@EnableResponseWrapper
abstract class AbstractCommonRestController<E : IBaseEntity, D : IBaseDto>(
    protected val clazz: KClass<E>,
    protected val service: AbstractBaseService<E>
) {
    @Autowired
    protected lateinit var mapper: IBaseMapper<E, D>

    @Autowired
    protected lateinit var repository: ICommonRestRepository<E>

    @Autowired
    protected lateinit var userService: UserService

    @GetMapping
    fun list(pageable: Pageable, @RequestParam filter: FilterModel?): Page<D> =
        repository
            .findAll(filter?.asSpec(), pageable)
            .map(mapper::toDto)

    @GetMapping("/all")
    fun listAll(): List<D> =
        service.getAll().map(mapper::toDto)

    @PostMapping
    fun create(@Valid @RequestBody rq: D): D =
        rq
            .let(mapper::toEntity)
            .let(service::create)
            .let(mapper::toDto)

    @GetMapping("/{id}")
    fun get(@PathVariable id: IdType): D =
        service.get(id)
            ?.let(mapper::toDto)
            ?: throw NotFoundException()

    @DeleteMapping("/{id}")
    fun remove(@PathVariable id: IdType) =
        service.remove(id)

    @PutMapping("/{id}")
    fun update(@PathVariable id: IdType, @Valid @RequestBody rq: D): D {
        return rq
            .let(mapper::toEntity)
            .let { service.update(id, it) }
            .let(mapper::toDto)
    }

    @GetMapping("/count")
    fun count() = service.count()
}
