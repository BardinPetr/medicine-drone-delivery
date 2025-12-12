package ru.bardinpetr.itmo.meddelivery.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ru.bardinpetr.itmo.meddelivery.common.search.CustomPageableResolver
import ru.bardinpetr.itmo.meddelivery.common.search.FilterModelQueryConverter

@Configuration
class WebConfig(
    val pageResolver: CustomPageableResolver
) : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(FilterModelQueryConverter())
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>?>) {
        super.configureMessageConverters(converters)
        converters.add(0, MappingJackson2HttpMessageConverter())
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(pageResolver)
    }
}
