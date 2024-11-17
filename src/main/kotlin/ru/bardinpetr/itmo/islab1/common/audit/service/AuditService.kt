package ru.bardinpetr.itmo.islab1.common.audit.service

import org.hibernate.envers.AuditReader
import org.hibernate.envers.query.AuditEntity
import org.hibernate.envers.query.AuditQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import ru.bardinpetr.itmo.islab1.common.audit.model.AuditLogEntry
import ru.bardinpetr.itmo.islab1.common.audit.model.typedResultList
import ru.bardinpetr.itmo.islab1.common.models.IBaseEntity
import kotlin.reflect.KClass

abstract class AuditService<T : IBaseEntity>(
    private val clazz: KClass<T>
) {
    @Autowired
    private lateinit var auditReader: AuditReader

    private fun queryByType(): AuditQuery =
        auditReader
            .createQuery()
            .forRevisionsOfEntity(clazz.java, false, true)

    /**
     * Returns audit log for all objects of type T
     */
    fun getAuditLog(): List<AuditLogEntry<T>> =
        queryByType()
            .typedResultList()

    /**
     * Returns audit log for item ot type T with specified id
     */
    fun getEntityAuditLog(id: Long): List<AuditLogEntry<T>> =
        queryByType()
            .add(AuditEntity.id().eq(id))
            .typedResultList()

    fun getAuditLog(pageable: Pageable): List<AuditLogEntry<T>> {
        return queryByType()
            .runCatching {
                return setFirstResult(pageable.pageNumber * pageable.pageSize)
                    .setMaxResults(pageable.pageSize)
                    .typedResultList()
            }
            .getOrDefault(emptyList())
    }
}
