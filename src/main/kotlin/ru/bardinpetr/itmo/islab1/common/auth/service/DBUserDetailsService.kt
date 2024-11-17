package ru.bardinpetr.itmo.islab1.common.auth.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.islab1.common.auth.model.UserRole
import org.springframework.security.core.userdetails.User as UserDetailsOfUser

@Service
class DBUserDetailsService(
    private val userService: UserService
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userService
            .find(username)
            ?.let {
                UserDetailsOfUser(
                    username,
                    it.passwordHash,
                    it.role
                        .run(::listOf)
                        .map(UserRole::name)
                        .map(::SimpleGrantedAuthority)
                )
            }
            ?: throw UsernameNotFoundException("User $username not found when fetching details")
}
