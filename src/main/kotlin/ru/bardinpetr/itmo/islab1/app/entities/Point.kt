package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.Embeddable

@Embeddable
data class Point(
    val lat: Double,
    val lon: Double
)