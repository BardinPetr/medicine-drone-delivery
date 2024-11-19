package ru.bardinpetr.itmo.meddelivery.app.dto

import ru.bardinpetr.itmo.meddelivery.app.entities.TaskStatus
import java.time.Instant

/**
 * DTO for {@link ru.bardinpetr.itmo.meddelivery.app.entities.FlightTask}
 */
data class FlightTaskDto(
    val requestId: Long? = null,
    val status: TaskStatus?,
    val productTypeProductTypeName: String?,
    val warehouseWarehouseName: String?,
    val medicalFacilityMedicalFacilityName: String?,
    val quantity: Int?,
    val routeId: Long? = null,
    val timestamp: Instant?,
    val id: Long? = null
)