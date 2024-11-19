package ru.bardinpetr.itmo.meddelivery.common.models

import ru.bardinpetr.itmo.meddelivery.common.auth.model.User

typealias IdType = Long

interface IBaseEntity : ITypedBaseEntity<IdType>

interface ITypedBaseEntity<I> {
    var id: I?
}

interface IBaseOwnedEntity : IBaseEntity {
    override var id: IdType?
    var owner: User?
}

fun IBaseEntity.tryCheckOwnership(user: User): Boolean =
    (this as? IBaseOwnedEntity)
        ?.owner
        ?.let { it.id == user.id }
        ?: false
