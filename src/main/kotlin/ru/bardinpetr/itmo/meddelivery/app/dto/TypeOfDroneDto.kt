package ru.bardinpetr.itmo.meddelivery.app.dto

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.TypeOfDrone}
 */
data class TypeOfDroneDto(val name: String?, val maxWeight: Long?, val speed: Double?, val id: Long? = null)