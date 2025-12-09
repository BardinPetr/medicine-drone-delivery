package ru.bardinpetr.itmo.meddelivery.app.modules.user

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.common.auth.dto.UserDto
import ru.bardinpetr.itmo.meddelivery.common.auth.dto.UserMapper
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.UserRepository
import ru.bardinpetr.itmo.meddelivery.common.auth.service.UserService
import ru.bardinpetr.itmo.meddelivery.common.handling.EnableResponseWrapper
import ru.bardinpetr.itmo.meddelivery.common.rest.search.FilterModel

@RestController
@RequestMapping("/api/user")
@EnableResponseWrapper
class UserController(
    private val userAuthService: UserService,
    private val userRepository: UserRepository,
    private val mapper: UserMapper
) {

    @GetMapping
    fun list(pageable: Pageable, @RequestParam filter: FilterModel?): Page<UserDto> =
        userRepository
            .findAll(filter?.asSpec(), pageable)
            .map(mapper::toDto)

    @GetMapping("/self")
    fun get() =
        userAuthService.getCurrent()!!

    @GetMapping("/all")
    fun listAll(): List<UserDto> =
        userRepository.findAll().map(mapper::toDto)
}
