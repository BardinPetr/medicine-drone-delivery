package ru.bardinpetr.itmo.meddelivery.common.auth.service.util

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

const val PASS_ALGO = "bcrypt"

@Configuration
class PasswordEncoderConfiguration {
    @Bean
    fun passwordEncoder(): PasswordEncoder =
        DelegatingPasswordEncoder(PASS_ALGO, mapOf(PASS_ALGO to BCryptPasswordEncoder()))
}
