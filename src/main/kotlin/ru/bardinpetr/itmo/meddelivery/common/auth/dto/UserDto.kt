package ru.bardinpetr.itmo.meddelivery.common.auth.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import ru.bardinpetr.itmo.meddelivery.common.auth.model.UserRole
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseDto

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