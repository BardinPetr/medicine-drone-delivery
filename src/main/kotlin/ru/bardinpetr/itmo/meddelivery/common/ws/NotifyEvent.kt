package ru.bardinpetr.itmo.meddelivery.common.ws

data class NotifyEvent(
    val className: String,
    val id: Long,
    val type: NotifyChangeType
)

enum class NotifyChangeType {
    ADD, MOD, REM
}
