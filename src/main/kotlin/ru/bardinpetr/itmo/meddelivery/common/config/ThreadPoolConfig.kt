package ru.bardinpetr.itmo.meddelivery.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class ThreadPoolConfig {
    @Bean
    fun threadPoolTaskScheduler() = ThreadPoolTaskScheduler().apply {
        poolSize = Runtime.getRuntime().availableProcessors()
        threadNamePrefix = "scheduler-"
        initialize()
    }
}