package ru.bardinpetr.itmo.islab1.common.auth.service.util

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.islab1.common.auth.model.UserPrincipal
import ru.bardinpetr.itmo.islab1.common.auth.model.UserRole
import java.util.*
import javax.crypto.SecretKey

const val ROLE_CLAIM = "role"

@Service
class JWTService {
    private final val key: SecretKey = Jwts.SIG.HS256.key().build()
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