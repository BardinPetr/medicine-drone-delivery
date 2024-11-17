package ru.bardinpetr.itmo.islab1.common.rest.base

import org.springframework.http.ResponseEntity

data class BaseResponse(
    val status: Int,
    val errors: List<String>,
    val data: Any?
) {
    companion object {
        fun ok(data: Any) = BaseResponse(200, emptyList(), data)
        fun error(status: Int, error: String) = BaseResponse(status, listOf(error), null)
        fun error(error: String) = error(400, error)
        fun errors(errors: Iterable<String>) = BaseResponse(400, errors.toList(), null)
    }

    fun toResponseEntity(): ResponseEntity<BaseResponse> = ResponseEntity.ok(this)
}

