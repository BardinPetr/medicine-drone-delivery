package ru.bardinpetr.itmo.meddelivery.app.events

import org.springframework.context.ApplicationEvent

class ExecutePlanningApplicationEvent(obj: Any) : ApplicationEvent(obj)
