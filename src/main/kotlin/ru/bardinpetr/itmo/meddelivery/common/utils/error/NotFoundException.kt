package ru.bardinpetr.itmo.meddelivery.common.utils.error

class NotFoundException : Exception("Not found")

fun notFound(): Nothing = throw NotFoundException()
