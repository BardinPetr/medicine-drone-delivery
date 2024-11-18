package ru.bardinpetr.itmo.meddelivery.app.modules.user

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.bardinpetr.itmo.meddelivery.app.dto.UserDto
import ru.bardinpetr.itmo.meddelivery.app.mapper.UserMapper
import ru.bardinpetr.itmo.meddelivery.common.auth.model.User
import ru.bardinpetr.itmo.meddelivery.common.auth.model.UserRole
import ru.bardinpetr.itmo.meddelivery.common.auth.repository.UserRepository
import ru.bardinpetr.itmo.meddelivery.common.auth.service.UserService
import ru.bardinpetr.itmo.meddelivery.common.handling.EnableResponseWrapper
import ru.bardinpetr.itmo.meddelivery.common.utils.error.NotFoundException
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/verify")
    fun verifyAdmin(@PathVariable id: Long): UserDto =
        userRepository
            .findById(id)
            .orElseThrow { NotFoundException() }
            .apply {
                if (role != UserRole.ADMIN_PENDING)
                    throw IllegalStateException("Not in list of pending admins")
            }
            .apply { role = UserRole.ADMIN }
            .let(userRepository::save)
            .let(mapper::toDto)
}