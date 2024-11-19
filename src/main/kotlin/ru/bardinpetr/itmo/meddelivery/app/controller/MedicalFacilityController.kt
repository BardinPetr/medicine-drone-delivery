
package ru.bardinpetr.itmo.meddelivery.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.MedicalFacilityDto
import ru.bardinpetr.itmo.meddelivery.app.entities.MedicalFacility
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController

@RequestMapping("/api/medicalfacility")
@RestController
class MedicalFacilityController : AbstractCommonRestController<MedicalFacility, MedicalFacilityDto>(MedicalFacility::class)
        