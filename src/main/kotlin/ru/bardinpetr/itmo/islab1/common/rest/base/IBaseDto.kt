package ru.bardinpetr.itmo.islab1.common.rest.base

interface IBaseDto {
    val id: Long?
    val ownerUsername: String?
    val ownerId: Long?
}