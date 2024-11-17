package ru.bardinpetr.itmo.islab1.common.audit.model

import jakarta.persistence.*
import org.hibernate.envers.RevisionEntity
import org.hibernate.envers.RevisionNumber
import org.hibernate.envers.RevisionTimestamp
import ru.bardinpetr.itmo.islab1.common.audit.service.AuditListener
import ru.bardinpetr.itmo.islab1.common.models.IBaseEntity
import java.time.Instant


typealias RevisionIdType = Long

@Entity
@RevisionEntity(AuditListener::class)
@Table(name = "rev_info")
class AuditRevisionEntry(
    @Temporal(TemporalType.TIMESTAMP)
    @RevisionTimestamp
    @Column(nullable = false)
    var timestamp: Instant,

    @Column(nullable = false)
    var owner: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    override var id: RevisionIdType? = null
) : IBaseEntity
