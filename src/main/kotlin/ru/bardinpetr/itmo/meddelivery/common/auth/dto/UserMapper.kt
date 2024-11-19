package ru.bardinpetr.itmo.meddelivery.common.auth.dto

import org.mapstruct.*
import ru.bardinpetr.itmo.meddelivery.common.auth.model.User

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
interface UserMapper {
    fun toEntity(userDto: UserDto): User

    fun toDto(user: User): UserDto

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    fun partialUpdate(userDto: UserDto, @MappingTarget user: User): User
}