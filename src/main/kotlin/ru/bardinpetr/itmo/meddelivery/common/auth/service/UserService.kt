package ru.bardinpetr.itmo.meddelivery.common.auth.service

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.common.auth.dto.LoginDto
import ru.bardinpetr.itmo.meddelivery.common.auth.dto.RegisterDto
import ru.bardinpetr.itmo.meddelivery.common.auth.dto.UserRsDto
import ru.bardinpetr.itmo.meddelivery.common.auth.mapper.toPrincipal
import ru.bardinpetr.itmo.meddelivery.common.auth.model.User
import ru.bardinpetr.itmo.meddelivery.common.auth.model.UserRole
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.UserRepository
import ru.bardinpetr.itmo.meddelivery.common.auth.service.util.JWTService

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtService: JWTService,
    private val passwordEncoder: PasswordEncoder
) {

    fun find(username: String): User? =
        userRepository.findUserByUsername(username)

    fun exists(username: String): Boolean =
        userRepository.existsUserByUsername(username)

    fun login(user: LoginDto): UserRsDto? =
        find(user.username)
            ?.also { require(passwordEncoder.matches(user.password, it.passwordHash)) { "Invalid password" } }
            ?.let(User::toPrincipal)
            ?.let {
                UserRsDto(
                    it.username,
                    it.role,
                    jwtService.encode(it)
                )
            }
            ?: throw IllegalArgumentException("User not found")

    fun register(user: RegisterDto): UserRsDto? {
        require(!exists(user.username)) { "User already exists" }
        require(user.role != UserRole.ADMIN || !userRepository.existsUserByRoleIs(UserRole.ADMIN)) {
            "Only once initial admin could be registered"
        }

        return user
            .let {
                User(
                    username = it.username,
                    passwordHash = passwordEncoder.encode(it.password),
                    role = it.role
                )
            }
            .let(userRepository::save)
            .let(User::toPrincipal)
            .let {
                UserRsDto(
                    it.username,
                    it.role,
                    jwtService.encode(it)
                )
            }
    }

    fun getCurrent(): User? =
        SecurityContextHolder
            .getContext()
            .authentication
            ?.name
            ?.let(::find)
}

fun getAuthenticatedUserDetails(): User? =
    SecurityContextHolder
        .getContext()
        ?.let { it.authentication as? UsernamePasswordAuthenticationToken }
        ?.let { it.principal as? User }
