package ru.bardinpetr.itmo.meddelivery.common.rest.base

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.utils.error.NotFoundException
import ru.bardinpetr.itmo.meddelivery.common.ws.NotifyChangeType
import ru.bardinpetr.itmo.meddelivery.common.ws.WebSocketNotifyService
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.KClass

abstract class AbstractBaseService<E : IBaseEntity>(
    protected val clazz: KClass<E>
) {

    @Autowired
    protected lateinit var repo: ICommonRestRepository<E>

    @Autowired
    protected lateinit var notifier: WebSocketNotifyService

    open fun get(id: IdType): E? = repo.findById(id).getOrNull()

    open fun getAll(): List<E> = repo.findAll()

    open fun count(): Long = repo.count()

    @Transactional
    open fun remove(id: IdType) {
        if (!repo.existsById(id)) throw NotFoundException()
        repo.deleteById(id)
        notifier.notifyChanges(clazz, id, NotifyChangeType.REM)
    }

    @Transactional
    open fun create(entity: E): E =
        repo.save(entity)
            .also { notifier.notifyChanges(clazz, it.id!!, NotifyChangeType.ADD) }

    @Transactional
    open fun update(id: IdType, patch: E): E =
        get(id)
            ?.apply { patch.id = id }
            ?.let(repo::save)
            ?.also { notifier.notifyChanges(clazz, id, NotifyChangeType.MOD) }
            ?: throw NotFoundException()
}
