package ru.bardinpetr.itmo.meddelivery.common.auth.model

data class UserPrincipal(
    val username: String,
    val role: UserRole
)

fun UserPrincipal.ofUser(user: User) = UserPrincipal(
    username = user.username,
    role = user.role
)
