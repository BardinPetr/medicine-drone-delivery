package ru.bardinpetr.itmo.meddelivery.common.rest.controller

import ru.bardinpetr.itmo.meddelivery.common.errors.NotAvailableException

fun AbstractCommonRestController<*, *>.deny(): Nothing =
    throw NotAvailableException()
