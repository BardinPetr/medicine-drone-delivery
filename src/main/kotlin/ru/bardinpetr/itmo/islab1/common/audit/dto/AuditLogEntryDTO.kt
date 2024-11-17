package ru.bardinpetr.itmo.islab1.common.audit.dto

import org.hibernate.envers.RevisionType

data class AuditLogEntryDTO<T>(
    val revision: Long,
    val type: RevisionType,
    val state: T,
    val user: Long,

    )