package ru.bardinpetr.itmo.islab1.common.audit.service

import org.hibernate.envers.RevisionListener
import ru.bardinpetr.itmo.islab1.common.audit.model.AuditRevisionEntry
import ru.bardinpetr.itmo.islab1.common.auth.service.getAuthenticatedUserDetails

class AuditListener : RevisionListener {
    override fun newRevision(revisionEntity: Any) {
        val entity = revisionEntity as AuditRevisionEntry
        entity.owner = getAuthenticatedUserDetails()?.username ?: "sys"
    }
}