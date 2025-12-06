package ru.bardinpetr.itmo.meddelivery.common.models

typealias IdType = Long

interface IBaseEntity : ITypedBaseEntity<IdType>

interface ITypedBaseEntity<I> {
    var id: I?
}
