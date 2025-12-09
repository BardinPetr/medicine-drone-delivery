package ru.bardinpetr.itmo.meddelivery.common.auth.service

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.bardinpetr.itmo.meddelivery.common.auth.service.util.JWTService

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: DBUserDetailsService,
    private val jwtService: JWTService
) : OncePerRequestFilter() {

    @Suppress("ReturnCount")
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null ||
            !authHeader.startsWith("Bearer") ||
            SecurityContextHolder.getContext().authentication != null
        ) {
            return filterChain.doFilter(request, response)
        }

        val jwt = authHeader
            .split(" ")
            .getOrNull(1)
            ?.let(jwtService::decode)
        if (jwt == null) {
            response.status = 401
            return
        }

        val userDetails = jwt
            .username
            .runCatching(userDetailsService::loadUserByUsername)
            .getOrElse {
                response.status = 401
                return
            }

        SecurityContextHolder
            .createEmptyContext()
            .apply {
                authentication =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                        .apply { details = WebAuthenticationDetailsSource().buildDetails(request) }
            }
            .apply(SecurityContextHolder::setContext)

        return filterChain.doFilter(request, response)
    }
}
