package ru.bardinpetr.itmo.meddelivery.app.repository

import ru.bardinpetr.itmo.meddelivery.app.entities.Route
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository

interface RouteRepository : ICommonRestRepository<Route> {
    fun findByWarehouseIdAndMedicalFacilityId(warehouseId: IdType, medicalFacilityId: IdType): Route?
}
