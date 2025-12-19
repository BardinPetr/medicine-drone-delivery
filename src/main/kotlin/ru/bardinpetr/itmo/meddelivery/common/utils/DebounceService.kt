package ru.bardinpetr.itmo.meddelivery.common.utils

import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.time.temporal.TemporalAmount
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ScheduledFuture

@Service
class DebounceService(
    @Qualifier("threadPoolTaskScheduler")
    private val taskScheduler: ThreadPoolTaskScheduler
) {
    private val scheduledTasks = ConcurrentHashMap<String, ScheduledFuture<*>>()
    private val lastCallTimes = ConcurrentHashMap<String, Instant>()

    fun debounce(key: String, period: TemporalAmount, maxPeriod: TemporalAmount, block: () -> Unit) {
        scheduledTasks.compute(key) { _, old ->
            old?.cancel(false)
            val lastCall = lastCallTimes.computeIfAbsent(key) { Instant.now() }
            val outPeriod =
                if (lastCall.isBefore(Instant.now().minus(maxPeriod))) Duration.ofMillis(10)
                else period

            taskScheduler.schedule({
                try {
                    block()
                } finally {
                    lastCallTimes.put(key, Instant.now())
                    scheduledTasks.remove(key)
                }
            }, Instant.now().plus(outPeriod))
        }
    }

    @PreDestroy
    fun cleanup() {
        scheduledTasks.forEach { _, f -> f.cancel(false) }
        scheduledTasks.clear()
    }
}