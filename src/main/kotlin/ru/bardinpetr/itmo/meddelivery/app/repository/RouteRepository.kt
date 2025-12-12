package ru.bardinpetr.itmo.meddelivery.app.repository

import ru.bardinpetr.itmo.meddelivery.app.entities.Route
import ru.bardinpetr.itmo.meddelivery.common.base.repo.ICommonRestRepository
import ru.bardinpetr.itmo.meddelivery.common.models.IdType

interface RouteRepository : ICommonRestRepository<Route> {
    fun findByWarehouseIdAndMedicalFacilityId(warehouseId: IdType, medicalFacilityId: IdType): Route?
}
