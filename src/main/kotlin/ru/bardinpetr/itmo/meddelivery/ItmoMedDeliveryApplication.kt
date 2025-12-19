package ru.bardinpetr.itmo.meddelivery

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.aspectj.EnableSpringConfigured
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import ru.bardinpetr.itmo.meddelivery.app.service.DemoDataGenerator

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@EnableSpringDataWebSupport
@EnableSpringConfigured
@EnableAsync
class ItmoMedDeliveryApplication {
    @Bean
    fun launchDataGenerator(gen: DemoDataGenerator) = CommandLineRunner { params ->
        if (params.firstOrNull() == "generate") gen.launch()
    }
}

fun main(args: Array<String>) {
    runApplication<ItmoMedDeliveryApplication>(*args)
}
