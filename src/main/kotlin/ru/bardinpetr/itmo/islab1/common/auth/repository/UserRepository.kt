package ru.bardinpetr.itmo.islab1.common.auth.repository

import ru.bardinpetr.itmo.islab1.common.auth.model.User
import ru.bardinpetr.itmo.islab1.common.auth.model.UserRole
import ru.bardinpetr.itmo.islab1.common.rest.base.ICommonRestRepository

interface UserRepository : ICommonRestRepository<User> {
    fun existsUserByRoleIs(role: UserRole): Boolean
    fun findUserByUsername(username: String): User?
    fun existsUserByUsername(username: String): Boolean
}