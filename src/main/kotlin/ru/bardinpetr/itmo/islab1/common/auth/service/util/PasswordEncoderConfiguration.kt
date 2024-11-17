package ru.bardinpetr.itmo.islab1.common.auth.service.util

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import ru.bardinpetr.itmo.islab1.common.auth.config.HASH_ALGO

@Configuration
class PasswordEncoderConfiguration {
    @Bean
    fun passwordEncoder(): PasswordEncoder =
        DelegatingPasswordEncoder(HASH_ALGO, mapOf(HASH_ALGO to MessageDigestPasswordEncoder(HASH_ALGO)))
}