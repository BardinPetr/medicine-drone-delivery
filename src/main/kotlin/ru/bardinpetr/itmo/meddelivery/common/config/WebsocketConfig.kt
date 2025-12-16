package ru.bardinpetr.itmo.meddelivery.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean

@Configuration
class WebsocketConfig {
    @Bean
    fun wsContainer() = ServletServerContainerFactoryBean()
        .apply {
            maxTextMessageBufferSize = 8192
            maxBinaryMessageBufferSize = 8192
        }
}
