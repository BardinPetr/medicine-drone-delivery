package ru.bardinpetr.itmo.meddelivery.common.audit.event

import org.hibernate.event.spi.*
import org.hibernate.persister.entity.EntityPersister
import org.springframework.stereotype.Component
import ru.bardinpetr.itmo.meddelivery.common.models.ITypedBaseEntity
import ru.bardinpetr.itmo.meddelivery.common.ws.INotificationService
import ru.bardinpetr.itmo.meddelivery.common.ws.NotifyChangeType

@Component
class EntityListenerService(
    val notifier: INotificationService,
) : PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {
    override fun onPostInsert(e: PostInsertEvent) {
        val entity = (e as? ITypedBaseEntity<*>) ?: return
        notifier.onEvent(entity, NotifyChangeType.ADD)
    }

    override fun onPostUpdate(e: PostUpdateEvent) {
        val entity = (e as? ITypedBaseEntity<*>) ?: return
        notifier.onEvent(entity, NotifyChangeType.MOD)
    }

    override fun onPostDelete(e: PostDeleteEvent) {
        val entity = (e as? ITypedBaseEntity<*>) ?: return
        notifier.onEvent(entity, NotifyChangeType.REM)
    }

    override fun requiresPostCommitHandling(p0: EntityPersister?): Boolean = true
}
