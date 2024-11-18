package ru.bardinpetr.itmo.meddelivery.common.handling

import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import ru.bardinpetr.itmo.meddelivery.common.rest.base.BaseResponse

@ControllerAdvice(annotations = [EnableResponseWrapper::class])
class ResponseControllerAdvice : ResponseBodyAdvice<Any> {
    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>
    ) = true

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any =
        if (body is ResponseEntity<*>)
            body
        else
            (body ?: BaseResponse.error(404, "Not found"))
                .let {
                    if (it is BaseResponse) it
                    else BaseResponse.ok(it)
                }
}