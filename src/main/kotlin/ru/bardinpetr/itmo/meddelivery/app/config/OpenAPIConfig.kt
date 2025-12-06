package ru.bardinpetr.itmo.meddelivery.app.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.HandlerMethod
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController
import java.util.*


@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "ITMO Lab1 API",
        version = "0.3.3"
    ),
    servers = [Server(
        url = "http://0.0.0.0:8080"
    ), Server(
        url = "http://meddelivery.bardinpetr.ru"
    )]
)
class OpenAPIConfiguration {
    @Bean
    fun customizeOpenAPI(): OpenAPI {
        val securitySchemeName = "bearerAuth"
        return OpenAPI()
            .addSecurityItem(
                SecurityRequirement()
                    .addList(securitySchemeName)
            )
            .components(
                Components()
                    .addSecuritySchemes(
                        securitySchemeName,
                        SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
    }

    @Bean
    fun operationIdCustomizer(): OperationCustomizer {
        return OperationCustomizer { operation: Operation, handlerMethod: HandlerMethod ->
            val superClazz = handlerMethod.beanType.superclass
            if (Objects.nonNull(superClazz) && superClazz.isAssignableFrom(AbstractCommonRestController::class.java)) {
                operation.operationId = String.format("%s", handlerMethod.method.name)
            }
            operation
        }
    }
}