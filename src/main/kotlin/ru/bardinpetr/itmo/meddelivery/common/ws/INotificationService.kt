package ru.bardinpetr.itmo.meddelivery.common.ws

import ru.bardinpetr.itmo.meddelivery.common.models.ITypedBaseEntity

interface INotificationService {
    fun onEvent(model: ITypedBaseEntity<*>, event: NotifyChangeType)
}
