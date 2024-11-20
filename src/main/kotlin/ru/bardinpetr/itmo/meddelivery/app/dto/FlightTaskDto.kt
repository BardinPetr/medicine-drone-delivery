package ru.bardinpetr.itmo.meddelivery.app.dto

import ru.bardinpetr.itmo.meddelivery.app.entities.TaskStatus
import ru.bardinpetr.itmo.meddelivery.common.rest.base.IBaseDto
import java.time.Instant

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.FlightTask}
 */
data class FlightTaskDto(
    val request: RequestDto?,
    val status: TaskStatus?,
    val productType: ProductTypeDto?,
    val warehouse: WarehouseDto? = null,
    val medicalFacility: MedicalFacilityDto? = null,
    val quantity: Int?,
    val route: RouteDto? = null,
    val timestamp: Instant?,
    override val id: Long? = null
) : IBaseDto