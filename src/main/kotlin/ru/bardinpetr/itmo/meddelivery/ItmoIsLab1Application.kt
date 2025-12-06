package ru.bardinpetr.itmo.meddelivery

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.aspectj.EnableSpringConfigured
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.scheduling.annotation.EnableScheduling
import ru.bardinpetr.itmo.meddelivery.app.modules.transport.DroneMover
import ru.bardinpetr.itmo.meddelivery.app.repository.DroneRepository

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@EnableSpringDataWebSupport
@EnableSpringConfigured
class ItmoMedDeliveryApplication {
    @Bean
    fun droneMover(droneRepository: DroneRepository): DroneMover = DroneMover(droneRepository)

    @Bean
    fun demo() = CommandLineRunner {}
}

fun main(args: Array<String>) {
    runApplication<ItmoMedDeliveryApplication>(*args)
}