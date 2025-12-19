package ru.bardinpetr.itmo.meddelivery.common.ws.model

import kotlinx.serialization.Serializable

@Serializable
enum class NotificationType {
    INSERT, UPDATE, DELETE, REFRESH
}
