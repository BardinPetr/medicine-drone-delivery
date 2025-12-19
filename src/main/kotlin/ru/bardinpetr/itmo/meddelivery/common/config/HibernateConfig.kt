package ru.bardinpetr.itmo.meddelivery.common.config

import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManagerFactory
import org.hibernate.event.service.spi.EventListenerRegistry
import org.hibernate.event.spi.EventType
import org.hibernate.internal.SessionFactoryImpl
import org.springframework.context.annotation.Configuration
import ru.bardinpetr.itmo.meddelivery.common.audit.event.EntityListenerService

@Configuration
class HibernateConfig(
    val entityManagerFactory: EntityManagerFactory,
    val listener: EntityListenerService
) {
    @PostConstruct
    fun init() {
        entityManagerFactory
            .unwrap(SessionFactoryImpl::class.java)
            .serviceRegistry
            .getService(EventListenerRegistry::class.java)
            ?.apply {
                appendListeners(EventType.POST_INSERT, listener)
                appendListeners(EventType.POST_UPDATE, listener)
                appendListeners(EventType.POST_DELETE, listener)
            }
    }
}
