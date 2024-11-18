package ru.bardinpetr.itmo.meddelivery.app.config

import jakarta.persistence.EntityManagerFactory
import org.hibernate.envers.AuditReader
import org.hibernate.envers.AuditReaderFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuditConfig {

    @Autowired
    private lateinit var entityManagerFactory: EntityManagerFactory

    @Bean
    fun createAuditReader(): AuditReader = AuditReaderFactory.get(entityManagerFactory.createEntityManager())
}