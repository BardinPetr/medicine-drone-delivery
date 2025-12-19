package ru.bardinpetr.itmo.meddelivery.common.ws.delivery

import ru.bardinpetr.itmo.meddelivery.common.ws.model.NotificationEventModel

interface INotificationTransport {
    fun send(events: List<NotificationEventModel>)
}
