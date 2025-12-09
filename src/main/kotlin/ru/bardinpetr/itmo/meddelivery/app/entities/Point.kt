package ru.bardinpetr.itmo.meddelivery.app.entities

import jakarta.persistence.Embeddable

@Embeddable
data class Point(
    var lat: Double,
    var lon: Double
)
