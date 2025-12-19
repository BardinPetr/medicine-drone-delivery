package ru.bardinpetr.itmo.meddelivery.common.ws

import ru.bardinpetr.itmo.meddelivery.common.models.ITypedBaseEntity
import ru.bardinpetr.itmo.meddelivery.common.ws.model.NotificationType

interface INotificationService {
    fun onEvent(model: ITypedBaseEntity<*>, event: NotificationType)
}
