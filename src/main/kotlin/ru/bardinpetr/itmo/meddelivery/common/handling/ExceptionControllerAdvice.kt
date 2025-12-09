package ru.bardinpetr.itmo.meddelivery.common.handling

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException
import ru.bardinpetr.itmo.meddelivery.common.rest.base.BaseResponse

@ControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler(Throwable::class)
    fun handleException(e: Throwable): ResponseEntity<BaseResponse> =
        BaseResponse
            .error(400, e.message ?: "Unknown error")
            .toResponseEntity()

    @ExceptionHandler(AuthorizationDeniedException::class)
    fun handleExceptionADE(e: Throwable): ResponseEntity<BaseResponse> =
        BaseResponse.error(403, "Access denied")
            .toResponseEntity()

    @ExceptionHandler(AccessDeniedException::class)
    fun handleExceptionAcDE(e: Throwable): ResponseEntity<BaseResponse> =
        BaseResponse.error(403, "Access denied")
            .toResponseEntity()

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleExceptionNRFE(e: Throwable): ResponseEntity<BaseResponse> =
        BaseResponse.error("Not found")
            .toResponseEntity()

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleExceptionDIVE(e: Throwable): ResponseEntity<BaseResponse> =
        BaseResponse.error("Not possible according to constraints")
            .toResponseEntity()

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleExceptionMEANVE(e: MethodArgumentNotValidException): ResponseEntity<BaseResponse> =
        e.fieldErrors
            .map { it.defaultMessage ?: "Invalid filed ${it.field}" }
            .let(BaseResponse::errors)
            .toResponseEntity()
}
