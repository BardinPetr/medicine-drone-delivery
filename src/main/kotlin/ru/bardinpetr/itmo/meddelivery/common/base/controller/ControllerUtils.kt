package ru.bardinpetr.itmo.meddelivery.common.base.controller

import ru.bardinpetr.itmo.meddelivery.common.errors.NotAvailableException

fun AbstractCommonRestController<*, *>.deny(): Nothing =
    throw NotAvailableException()
