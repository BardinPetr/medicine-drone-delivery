package ru.bardinpetr.itmo.islab1.common.auth.mapper

import ru.bardinpetr.itmo.islab1.common.auth.dto.RegisterDto
import ru.bardinpetr.itmo.islab1.common.auth.model.User
import ru.bardinpetr.itmo.islab1.common.auth.model.UserPrincipal

fun RegisterDto.toPrincipal() =
    UserPrincipal(this.username, this.role)

fun User.toPrincipal() =
    UserPrincipal(this.username, this.role)