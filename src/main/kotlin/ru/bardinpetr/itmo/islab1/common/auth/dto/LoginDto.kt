package ru.bardinpetr.itmo.islab1.common.auth.dto

import ru.bardinpetr.itmo.islab1.common.auth.model.UserRole

data class LoginDto(val username: String, val password: String)

data class UserRsDto(val username: String, val role: UserRole, val token: String)