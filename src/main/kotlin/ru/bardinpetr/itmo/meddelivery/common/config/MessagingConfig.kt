package ru.bardinpetr.itmo.meddelivery.common.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

const val BASE_STOMP_TOPIC = "/app"

@EnableWebSocketMessageBroker
@Component
class MessagingConfig : WebSocketMessageBrokerConfigurer {

    @Lazy
    @Autowired
    private lateinit var messageBrokerTaskScheduler: TaskScheduler

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*")
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker(BASE_STOMP_TOPIC)
            .setHeartbeatValue(longArrayOf(10000, 10000))
            .setTaskScheduler(messageBrokerTaskScheduler)
    }
}
