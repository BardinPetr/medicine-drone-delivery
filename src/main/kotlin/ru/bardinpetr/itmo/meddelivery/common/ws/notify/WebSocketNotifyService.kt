package ru.bardinpetr.itmo.meddelivery.common.ws

import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.common.models.ITypedBaseEntity
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.utils.DebounceService
import ru.bardinpetr.itmo.meddelivery.common.ws.delivery.INotificationTransport
import ru.bardinpetr.itmo.meddelivery.common.ws.model.NotificationEventKey
import ru.bardinpetr.itmo.meddelivery.common.ws.model.NotificationEventModel
import ru.bardinpetr.itmo.meddelivery.common.ws.model.NotificationType
import java.time.Duration
import java.util.concurrent.CopyOnWriteArrayList

@Service
class WebSocketNotifyService(
    private val transport: INotificationTransport,
    private val debounce: DebounceService,
    @Value("\${app.notify.enabled:}")
    val entitiesEnableListParam: String,
    @Value("\${app.notify.min-period-ms:300}")
    val minimumPeriodMs: Long,
    @Value("\${app.notify.max-period-ms:1000}")
    val maximumPeriodMs: Long,
) : INotificationService {

    private val enabledClassnames: List<String> by lazy { entitiesEnableListParam.split(",").map { it.trim() } }
    private val events: MutableList<NotificationEventModel> = CopyOnWriteArrayList()

    @Async
    override fun onEvent(model: ITypedBaseEntity<*>, type: NotificationType) {
        val id = model.id as? IdType
        val key = NotificationEventKey(
            className = entityClassname(model).also { if (!enabledClassnames.contains(it)) return },
            eventType = if (id == null) NotificationType.REFRESH else type
        )
        addEvent(NotificationEventModel(id, key))
    }

    private fun addEvent(evt: NotificationEventModel) {
        events.add(evt)
        debounce.debounce("event-queue-push", Duration.ofMillis(minimumPeriodMs), Duration.ofMillis(maximumPeriodMs)) {
            transport.send(events)
            events.clear()
        }
    }

    private fun entityClassname(model: ITypedBaseEntity<*>): String =
        model::class
            .simpleName!!
            .replace(Regex("Model|Entity"), "")
}
