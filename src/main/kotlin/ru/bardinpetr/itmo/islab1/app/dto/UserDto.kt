package ru.bardinpetr.itmo.islab1.app.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import ru.bardinpetr.itmo.islab1.common.auth.model.UserRole
import ru.bardinpetr.itmo.islab1.common.rest.base.IBaseDto

data class UserDto(
    @field:NotBlank
    @field:NotNull
    val username: String?,
    @field:NotNull
    val role: UserRole?,
    override val id: Long? = null,
    override val ownerUsername: String? = null,
    override val ownerId: Long? = null
) : IBaseDto