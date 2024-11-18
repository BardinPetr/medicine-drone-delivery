package ru.bardinpetr.itmo.meddelivery.common.auth.dto

import ru.bardinpetr.itmo.meddelivery.common.auth.model.UserRole

data class LoginDto(val username: String, val password: String)

data class UserRsDto(val username: String, val role: UserRole, val token: String)