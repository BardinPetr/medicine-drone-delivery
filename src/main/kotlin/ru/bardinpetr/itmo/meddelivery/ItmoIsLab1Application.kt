package ru.bardinpetr.itmo.meddelivery

import DroneMover
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.aspectj.EnableSpringConfigured
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.scheduling.annotation.EnableScheduling
import ru.bardinpetr.itmo.meddelivery.app.modules.transport.DroneSender
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.DroneRepository
import ru.bardinpetr.itmo.meddelivery.common.utils.logger

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@EnableSpringDataWebSupport
@EnableSpringConfigured
class ItmoMedDeliveryApplication {
    val log = logger<ItmoMedDeliveryApplication>()

    @Bean
    fun droneMover(droneRepository: DroneRepository): DroneMover = DroneMover(droneRepository)

    @Bean
    fun demo(
        gen: TestDataGen,
        droneSender: DroneSender,
        droneRepository: DroneRepository
    ) = CommandLineRunner {
        gen.createTestData()
//        droneSender.sendDrone(droneRepository.findById(1).get())
    }
}

fun main(args: Array<String>) {
    runApplication<ItmoMedDeliveryApplication>(*args)
}