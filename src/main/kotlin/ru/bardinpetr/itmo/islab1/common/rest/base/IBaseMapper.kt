package ru.bardinpetr.itmo.meddelivery.common.rest.base

import ru.bardinpetr.itmo.meddelivery.common.models.IBaseOwnedEntity


interface IBaseMapper<E : IBaseOwnedEntity, D : IBaseDto> {
    fun toDto(e: E): D
    fun toEntity(d: D): E
    fun partialUpdate(d: D, u: E): E
}