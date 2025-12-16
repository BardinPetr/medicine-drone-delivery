package ru.bardinpetr.itmo.meddelivery.common.base.service

import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.common.base.repo.ICommonRestRepository
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.utils.error.NotFoundException
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.KClass

abstract class AbstractBaseService<E : IBaseEntity>(
    protected val clazz: KClass<E>,
    protected open val repo: ICommonRestRepository<E>
) {
    open fun get(id: IdType): E? = repo.findById(id).getOrNull()

    open fun getAll(): List<E> = repo.findAll()

    open fun count(): Long = repo.count()

    @Transactional
    open fun remove(id: IdType) {
        if (!repo.existsById(id)) throw NotFoundException()
        repo.deleteById(id)
    }

    @Transactional
    open fun create(entity: E): E =
        repo.save(entity)

    @Transactional
    open fun update(id: IdType, patch: E): E =
        get(id)
            ?.apply { patch.id = id }
            ?.let(repo::save)
            ?: throw NotFoundException()
}
