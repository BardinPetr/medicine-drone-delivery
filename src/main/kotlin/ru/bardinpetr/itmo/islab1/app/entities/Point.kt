package ru.bardinpetr.itmo.islab1.app.entities

import jakarta.persistence.Embeddable

@Embeddable
data class Point(
    val lat: Double,
    val lon: Double
)