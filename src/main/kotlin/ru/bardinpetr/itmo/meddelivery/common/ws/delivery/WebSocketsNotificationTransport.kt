package ru.bardinpetr.itmo.meddelivery.common.ws.delivery

import kotlinx.serialization.json.Json
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.service.drone.DroneService
import ru.bardinpetr.itmo.meddelivery.common.config.BASE_STOMP_TOPIC
import ru.bardinpetr.itmo.meddelivery.common.ws.model.NotificationEventModel
import ru.bardinpetr.itmo.meddelivery.common.ws.model.NotificationEvents
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Service
class WebSocketsNotificationTransport(
    private val msgTemplate: SimpMessagingTemplate,
    val droneService: DroneService,
) : INotificationTransport {

    @Scheduled(fixedDelay = 100, timeUnit = TimeUnit.MILLISECONDS)
    fun demo() {
        val d = droneService.getAll()
            .also { if (it.isEmpty()) return }
            .random().id!!
        droneService.updateDrone(d) {
            location.lat += Random.nextDouble(-0.001, 0.001)
            location.lon += Random.nextDouble(-0.001, 0.001)
        }
    }

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

