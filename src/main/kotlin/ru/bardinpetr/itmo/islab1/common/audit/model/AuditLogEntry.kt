@file:Suppress("UNCHECKED_CAST")

package ru.bardinpetr.itmo.meddelivery.common.audit.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import org.hibernate.envers.RevisionType
import org.hibernate.envers.query.AuditQuery
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity
import java.time.LocalDateTime
import java.time.ZoneId

data class AuditLogEntry<T>(
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val timestamp: LocalDateTime,
    val author: String,
    val revision: Long,
    val type: RevisionType,
    val entityId: Long
)

fun <T : IBaseEntity> AuditQuery.typedResultList(): List<AuditLogEntry<T>> =
    resultList
        .map { it as Array<*> }
        .mapNotNull {
            val entry = (it[1] as? AuditRevisionEntry) ?: return@mapNotNull null
            val state = (it[0] as? T) ?: return@mapNotNull null
            AuditLogEntry(
                revision = entry.id!!,
                author = entry.owner,
                timestamp = LocalDateTime.ofInstant(entry.timestamp, ZoneId.systemDefault()),
                type = (it[2] as? RevisionType) ?: return@mapNotNull null,
                entityId = state.id!!
            )
        }
