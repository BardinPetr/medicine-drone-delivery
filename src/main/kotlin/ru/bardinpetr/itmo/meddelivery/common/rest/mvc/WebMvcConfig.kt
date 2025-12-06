package ru.bardinpetr.itmo.meddelivery.common.rest.mvc

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ru.bardinpetr.itmo.meddelivery.common.rest.search.CustomPageableResolver


@Configuration
class WebMvcConfig(
    val pageResolver: CustomPageableResolver
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(pageResolver)
    }
}
