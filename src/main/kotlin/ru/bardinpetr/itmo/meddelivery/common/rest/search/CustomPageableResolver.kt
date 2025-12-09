package ru.bardinpetr.itmo.meddelivery.common.rest.search

import org.springframework.core.MethodParameter
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.stereotype.Service
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * For compliance with openapigenerator as it does not support to generate multiple GET values with same key
 */
@Service
class CustomPageableResolver : PageableHandlerMethodArgumentResolver() {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return Pageable::class.java.isAssignableFrom(parameter.parameterType)
    }

    override fun resolveArgument(
        methodParameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Pageable {
        val page = webRequest.getParameter(pageParameterName)?.toIntOrNull() ?: 0
        val size = webRequest.getParameter(sizeParameterName)?.toIntOrNull() ?: 100

        val sort = webRequest
            .getParameter("sort")
            ?.split(";")
            ?.mapNotNull { part ->
                part
                    .split(",")
                    .map { it.trim() }
                    .takeIf { it.size == 2 }
                    ?.let {
                        var (field, dirName) = it
                        val direction = Sort.Direction.valueOf(dirName.uppercase())
                        Sort.Order(direction, field)
                    }
            }
            ?.takeIf { it.isNotEmpty() }
            ?.let(Sort::by)
            ?: Sort.unsorted()

        return PageRequest.of(page, size, sort)
    }
}
