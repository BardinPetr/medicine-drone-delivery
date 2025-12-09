package ru.bardinpetr.itmo.meddelivery.common.auth.repository

import ru.bardinpetr.itmo.meddelivery.common.auth.model.User
import ru.bardinpetr.itmo.meddelivery.common.auth.model.UserRole
import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository

interface UserRepository : ICommonRestRepository<User> {
    fun existsUserByRoleIs(role: UserRole): Boolean
    fun findUserByUsername(username: String): User?
    fun existsUserByUsername(username: String): Boolean
    fun findAllByRoleIs(role: UserRole): List<User>
}