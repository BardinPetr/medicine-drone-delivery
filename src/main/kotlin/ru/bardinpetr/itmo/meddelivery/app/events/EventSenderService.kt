package ru.bardinpetr.itmo.meddelivery.app.events

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class EventSenderService(
    private val publisher: ApplicationEventPublisher
) {
    fun sendProcessPlans() {
        publisher.publishEvent(ExecutePlanningApplicationEvent(this))
    }
}
