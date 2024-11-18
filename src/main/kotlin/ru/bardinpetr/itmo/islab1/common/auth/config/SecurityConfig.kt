package ru.bardinpetr.itmo.meddelivery.common.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ru.bardinpetr.itmo.meddelivery.common.auth.service.DBUserDetailsService
import ru.bardinpetr.itmo.meddelivery.common.auth.service.JwtAuthenticationFilter


const val HASH_ALGO: String = "SHA-512"

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true
)
class SecurityConfig(
    private val userDetailsService: DBUserDetailsService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .cors(withDefaults())
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests {
                it
                    .anyRequest().permitAll()
//                    .requestMatchers(
//                        AntPathRequestMatcher("/docs*"),
//                        AntPathRequestMatcher("/docs/*"),
//                        AntPathRequestMatcher("/swagger-ui/*"),
//                        AntPathRequestMatcher("/auth/register"),
//                        AntPathRequestMatcher("/auth/login"),
//                        AntPathRequestMatcher("/auth/**"),
//                        AntPathRequestMatcher("/ws"),
//                        AntPathRequestMatcher("/ws/**")
//                    ).permitAll()
//                    .anyRequest().authenticated()
            }
            .exceptionHandling {
                it.accessDeniedHandler { request, response, accessDeniedException ->
                    response.status = 403
                }
            }
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }


    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry
                    .addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedOrigins("*")
                    .allowedMethods("*")
            }
        }
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider =
        DaoAuthenticationProvider()
            .apply {
                setUserDetailsService(userDetailsService)
                setPasswordEncoder(passwordEncoder)
            }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager
}