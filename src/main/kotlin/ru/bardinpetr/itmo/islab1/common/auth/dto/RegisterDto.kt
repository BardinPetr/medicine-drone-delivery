package ru.bardinpetr.itmo.islab1.common.auth.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import ru.bardinpetr.itmo.islab1.common.auth.model.UserRole

data class RegisterDto(
    val username: String,
    @field:NotBlank
    @field:Size(min = 8, max = 255, message = "Password minimum length: 8")
    val password: String,
    val role: UserRole
)