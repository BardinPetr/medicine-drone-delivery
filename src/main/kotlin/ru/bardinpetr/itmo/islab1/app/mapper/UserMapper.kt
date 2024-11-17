package ru.bardinpetr.itmo.islab1.app.mapper

import org.mapstruct.*
import ru.bardinpetr.itmo.islab1.app.dto.UserDto
import ru.bardinpetr.itmo.islab1.common.auth.model.User

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
interface UserMapper {
    fun toEntity(userDto: UserDto): User

    fun toDto(user: User): UserDto

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    fun partialUpdate(userDto: UserDto, @MappingTarget user: User): User
}