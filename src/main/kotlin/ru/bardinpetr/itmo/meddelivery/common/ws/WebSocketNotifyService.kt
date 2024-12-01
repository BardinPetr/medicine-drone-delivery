package ru.bardinpetr.itmo.meddelivery.common.ws

import org.fusesource.mqtt.client.QoS
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass


@Service
class WebSocketNotifyService(
    private val mqtt: MQTTConn
) {

    private val events: MutableMap<String, MutableList<NotifyEvent>> = mutableMapOf()

    fun notifyChanges(entityClazz: KClass<*>, id: Long, type: NotifyChangeType) {
        val name = entityClazz.simpleName!!
        events
            .putIfAbsent(name, mutableListOf())
            ?.add(NotifyEvent(name, id, type))
    }

    @Scheduled(fixedRate = 750)
    @Transactional
    fun check() {
        publish("/notify", "OK")
        for (event in events) {
            if (event.value.isEmpty()) continue
            publish("/notify/${event.key}", "UPD")
            event.value.clear()
        }
    }

    private fun publish(topic: String, data: String) {
        mqtt.publish(topic, data.toByteArray(), QoS.AT_LEAST_ONCE, false)
    }
}
