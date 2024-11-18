package ru.bardinpetr.itmo.meddelivery.common.rest.base

interface IBaseDto {
    val id: Long?
    val ownerUsername: String?
    val ownerId: Long?
}