package ru.bardinpetr.itmo.meddelivery.app.dto

import ru.bardinpetr.itmo.meddelivery.app.entities.drones.TaskStatus
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseDto

data class RequestDto(
    val userUsername: String?,
    val status: TaskStatus?,
    val medicalFacilityName: String?,
    val requestEntries: MutableList<RequestEntryDto> = mutableListOf(),
    override val id: Long? = null
) : IBaseDto
