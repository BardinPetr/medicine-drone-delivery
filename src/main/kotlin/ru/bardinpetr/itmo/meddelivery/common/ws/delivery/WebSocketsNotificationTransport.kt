package ru.bardinpetr.itmo.meddelivery.common.ws.delivery

import kotlinx.serialization.json.Json
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.common.config.BASE_STOMP_TOPIC
import ru.bardinpetr.itmo.meddelivery.common.ws.model.NotificationEventModel
import ru.bardinpetr.itmo.meddelivery.common.ws.model.NotificationEvents

@Service
class WebSocketsNotificationTransport(
    private val msgTemplate: SimpMessagingTemplate
) : INotificationTransport {

    override fun send(events: List<NotificationEventModel>) {
        events
            .groupBy { it.eventKey }
            .map { (key, list) ->
                NotificationEvents(
                    key,
                    list.mapNotNull { it.objectId }.distinct(),
                    list.minOf { it.timestamp },
                )
            }
            .forEach(::deliver)
    }

    private fun deliver(content: NotificationEvents) {
        val body = Json.encodeToString(NotificationEvents.serializer(), content)
        msgTemplate.convertAndSend("$BASE_STOMP_TOPIC/${content.eventKey.className}", body)
    }
}

