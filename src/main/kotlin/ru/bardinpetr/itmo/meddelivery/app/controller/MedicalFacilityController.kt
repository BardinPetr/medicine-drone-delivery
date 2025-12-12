package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.MedicalFacilityDto
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.common.base.controller.AbstractCommonRestController
import ru.bardinpetr.itmo.meddelivery.common.base.controller.deny
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.models.IdType

@RequestMapping("/api/medicalfacility")
@RestController
class MedicalFacilityController(service: AbstractBaseService<MedicalFacility>) :
    AbstractCommonRestController<MedicalFacility, MedicalFacilityDto>(MedicalFacility::class, service) {
    override fun remove(id: IdType) = deny()
    override fun update(id: IdType, rq: MedicalFacilityDto) = deny()
}
