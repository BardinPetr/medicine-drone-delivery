package ru.bardinpetr.itmo.meddelivery.common.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T : Any> logger(): Logger =
    LoggerFactory.getLogger(T::class.java)

inline fun <reified T : Any> logger(name: String): Logger =
    LoggerFactory.getLogger("${T::class.java.simpleName}/$name")
