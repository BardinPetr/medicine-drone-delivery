package ru.bardinpetr.itmo.meddelivery.common.rest.controller

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.bardinpetr.itmo.meddelivery.common.audit.model.AuditLogEntry
import ru.bardinpetr.itmo.meddelivery.common.audit.service.AuditService
import ru.bardinpetr.itmo.meddelivery.common.auth.model.UserRole
import ru.bardinpetr.itmo.meddelivery.common.auth.service.UserService
import ru.bardinpetr.itmo.meddelivery.common.handling.EnableResponseWrapper
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseOwnedEntity
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.models.tryCheckOwnership
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseDto
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseMapper
import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository
import ru.bardinpetr.itmo.meddelivery.common.rest.search.FilterModel
import ru.bardinpetr.itmo.meddelivery.common.utils.error.NotFoundException
import ru.bardinpetr.itmo.meddelivery.common.ws.NotifyChangeType
import ru.bardinpetr.itmo.meddelivery.common.ws.WebSocketNotifyService
import kotlin.reflect.KClass

@Validated
@EnableResponseWrapper
abstract class AbstractCommonRestController<E : IBaseOwnedEntity, D : IBaseDto>(
    private val clazz: KClass<E>,
) {

    @Autowired
    protected lateinit var notifier: WebSocketNotifyService

    @Autowired
    protected lateinit var mapper: IBaseMapper<E, D>

    @Autowired
    protected lateinit var repository: ICommonRestRepository<E>

    @Autowired
    protected lateinit var auditService: AuditService<E>

    @Autowired
    protected lateinit var userService: UserService

    @GetMapping
    fun list(pageable: Pageable, @RequestParam filter: FilterModel?): Page<D> =
        repository
            .findAll(filter?.asSpec(), pageable)
            .map(mapper::toDto)

    @GetMapping("/all")
    fun listAll(): List<D> =
        repository.findAll().map(mapper::toDto)

    @PostMapping
    fun create(@Valid @RequestBody rq: D): D =
        rq
            .let(mapper::toEntity)
            .apply(::preCreateHook)
            .let(repository::save)
            .let(mapper::toDto)
            .also { notifier.notifyChanges(clazz, it.id!!, NotifyChangeType.ADD) }

    protected fun preCreateHook(e: E) {
        e.owner = userService.getCurrent()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: IdType): D =
        repository
            .findById(id)
            .orElseThrow { NotFoundException() }
            .let(mapper::toDto)

    @DeleteMapping("/{id}")
    fun remove(@PathVariable id: IdType): Boolean {
        repository
            .findById(id)
            .orElseThrow { NotFoundException() }
            .also(::raiseForOwnership)
            .let(repository::delete)
            .also { notifier.notifyChanges(clazz, id, NotifyChangeType.REM) }
        return true
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: IdType, @Valid @RequestBody rq: D): D {
        val original =
            repository
                .findById(id)
                .orElseThrow { NotFoundException() }
                .also(::raiseForOwnership)
        return rq
            .let(mapper::toEntity)
            .apply { preUpdateHook(original, this) }
            .let(repository::save)
            .let(mapper::toDto)
            .also { notifier.notifyChanges(clazz, id, NotifyChangeType.MOD) }
    }

    protected fun preUpdateHook(old: E, next: E) {
        next.id = old.id
        next.owner = old.owner
    }

    @GetMapping("/count")
    fun count() = repository.count()

    @GetMapping("/audit")
    fun audit(pageable: Pageable): List<AuditLogEntry<E>> =
        auditService.getAuditLog(pageable)

    @GetMapping("/{id}/audit")
    fun auditItem(@PathVariable id: IdType): List<AuditLogEntry<E>>? {
        if (!repository.existsById(id))
            throw NotFoundException()
        return auditService.getEntityAuditLog(id)
    }

    private fun raiseForOwnership(item: IBaseEntity): Unit =
        userService
            .getCurrent()!!
            .let {
                if (!item.tryCheckOwnership(it) && it.role != UserRole.ADMIN)
                    throw IllegalAccessException("Entity not owned")
            }
}
