package ru.bardinpetr.itmo.meddelivery.common.ws.model

import kotlinx.serialization.Serializable
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.utils.DateSerializer
import java.time.LocalDateTime

@Serializable
data class NotificationEventModel(
    val objectId: IdType?,
    val eventKey: NotificationEventKey,
    @Serializable(with = DateSerializer::class)
    val timestamp: LocalDateTime = LocalDateTime.now(),
)

@Serializable
data class NotificationEventKey(val className: String, val eventType: NotificationType)

@Serializable
data class NotificationEvents(
    val eventKey: NotificationEventKey,
    val objects: List<IdType>,
    @Serializable(with = DateSerializer::class)
    val timestamp: LocalDateTime = LocalDateTime.now(),
)
