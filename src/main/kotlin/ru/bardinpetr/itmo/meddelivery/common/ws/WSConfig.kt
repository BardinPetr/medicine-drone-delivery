package ru.bardinpetr.itmo.meddelivery.common.ws

import org.fusesource.mqtt.client.BlockingConnection
import org.fusesource.mqtt.client.MQTT
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.bardinpetr.itmo.meddelivery.common.utils.logger

typealias MQTTConn = BlockingConnection

@Configuration
class MQTTConfig(
    @Value("\${app.mqtt.host:127.0.0.1}")
    val mqttHost: String,
    @Value("\${app.mqtt.port:1883}")
    val mqttPort: Int,
) {
    val log = logger<MQTTConfig>()

    @Bean
    fun getMQTTConn(): MQTTConn =
        MQTT()
            .apply {
                log.info("MQTT connecting to $mqttHost:$mqttPort")
                setHost(mqttHost, mqttPort)
            }
            .blockingConnection()
            .apply {
                log.info("MQTT connected")
                connect()
            }
}
