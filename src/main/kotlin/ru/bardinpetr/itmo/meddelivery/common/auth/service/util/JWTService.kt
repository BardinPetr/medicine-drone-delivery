package ru.bardinpetr.itmo.meddelivery.common.auth.service.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.common.auth.model.UserPrincipal
import ru.bardinpetr.itmo.meddelivery.common.auth.model.UserRole
import java.util.*
import javax.crypto.SecretKey

const val ROLE_CLAIM = "role"

@Service
class JWTService(
    @Value("\${app.jwt.secret:}")
    private val appSecretKey: String = ""
) {
    private final val key: SecretKey =
        appSecretKey
            .takeIf { it.isNotEmpty() && it.length >= 32 }
            ?.let { Keys.hmacShaKeyFor(it.encodeToByteArray()) }
            ?: Jwts.SIG.HS256.key().build()

    private final val decoder = Jwts
        .parser()
        .verifyWith(key)
        .build()

    fun encode(user: UserPrincipal): String {
        return Jwts
            .builder()
            .subject(user.username)
            .claim(ROLE_CLAIM, user.role.name)
            .issuedAt(Date())
            .id(UUID.randomUUID().toString())
            .signWith(key)
            .compact()
    }

    fun decode(jwt: String): UserPrincipal? =
        jwt
            .runCatching(decoder::parseSignedClaims)
            .mapCatching {
                UserPrincipal(
                    username = it.payload.subject,
                    role = UserRole.valueOf(it.payload["role"] as String)
                )
            }
            .getOrNull()
}