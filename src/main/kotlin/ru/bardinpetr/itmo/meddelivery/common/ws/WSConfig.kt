package ru.bardinpetr.itmo.meddelivery.common.ws

import org.fusesource.mqtt.client.BlockingConnection
import org.fusesource.mqtt.client.MQTT
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

typealias MQTTConn = BlockingConnection

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "mqtt")
@Configuration
class MQTTConfig {

    var mqttHost: String = "127.0.0.1"

    @Bean
    fun getMQTTConn(): MQTTConn =
        MQTT()
            .apply { setHost(mqttHost, 11883) }
            .blockingConnection()
            .apply { connect() }
}
