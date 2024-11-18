package ru.bardinpetr.itmo.meddelivery.common.auth.controller

import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.common.auth.dto.LoginDto
import ru.bardinpetr.itmo.meddelivery.common.auth.dto.RegisterDto
import ru.bardinpetr.itmo.meddelivery.common.auth.dto.UserRsDto
import ru.bardinpetr.itmo.meddelivery.common.auth.service.UserService
import ru.bardinpetr.itmo.meddelivery.common.handling.EnableResponseWrapper

@Validated
@RestController
@EnableResponseWrapper
@RequestMapping("/auth")
class LoginController(
    private val userService: UserService
) {

    @PostMapping("/login")
    fun login(@RequestBody req: LoginDto): UserRsDto? =
        userService.login(req)

    @PostMapping("/register")
    fun register(@Valid @RequestBody req: RegisterDto): UserRsDto? =
        userService.register(req)
}