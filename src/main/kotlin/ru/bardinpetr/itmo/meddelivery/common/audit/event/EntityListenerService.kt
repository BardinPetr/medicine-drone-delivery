package ru.bardinpetr.itmo.meddelivery.common.audit.event

import org.hibernate.event.spi.*
import org.hibernate.persister.entity.EntityPersister
import org.springframework.stereotype.Component
import ru.bardinpetr.itmo.meddelivery.common.models.ITypedBaseEntity
import ru.bardinpetr.itmo.meddelivery.common.ws.INotificationService
import ru.bardinpetr.itmo.meddelivery.common.ws.model.NotificationType

@Component
class EntityListenerService(
    val notifier: INotificationService,
) : PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {
    override fun onPostInsert(e: PostInsertEvent) {
        val entity = (e.entity as? ITypedBaseEntity<*>) ?: return
        notifier.onEvent(entity, NotificationType.INSERT)
    }

    override fun onPostUpdate(e: PostUpdateEvent) {
        val entity = (e.entity as? ITypedBaseEntity<*>) ?: return
        notifier.onEvent(entity, NotificationType.UPDATE)
    }

    override fun onPostDelete(e: PostDeleteEvent) {
        val entity = (e.entity as? ITypedBaseEntity<*>) ?: return
        notifier.onEvent(entity, NotificationType.DELETE)
    }

    override fun requiresPostCommitHandling(p0: EntityPersister?): Boolean = true
}
