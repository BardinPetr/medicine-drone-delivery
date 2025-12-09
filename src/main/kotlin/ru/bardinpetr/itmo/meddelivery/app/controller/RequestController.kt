package ru.bardinpetr.itmo.meddelivery.app.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.RequestDto
import ru.bardinpetr.itmo.meddelivery.app.entities.Request
import ru.bardinpetr.itmo.meddelivery.app.entities.RequestEntry
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.TaskStatus
import ru.bardinpetr.itmo.meddelivery.app.repository.MedicalFacilityRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.ProductTypeRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.RequestEntryRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.RequestRepository
import ru.bardinpetr.itmo.meddelivery.app.service.RequestService
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.deny
import ru.bardinpetr.itmo.meddelivery.common.ws.NotifyChangeType

@RequestMapping("/api/request")
@RestController
class RequestController(
    val mfRepo: MedicalFacilityRepository,
    val requestRepository: RequestRepository,
    val productTypeRepository: ProductTypeRepository,
    val entryRepository: RequestEntryRepository,
    override val service: RequestService
) : AbstractCommonRestController<Request, RequestDto>(Request::class, service) {

    override fun update(id: IdType, rq: RequestDto): RequestDto = deny()

    @PostMapping
    override fun create(@Valid @RequestBody rq: RequestDto): RequestDto {
        val user = userService.getCurrent()!!
        val request = requestRepository.save(
            Request(
                user = user,
                status = TaskStatus.QUEUED,
                medicalFacility = mfRepo.getFirstByResponsibleUserId(user.id!!)
            )
        )
        rq.requestEntries
            .map {
                RequestEntry(
                    request = request,
                    productType = it.productTypeType?.let(productTypeRepository::findByType)!!,
                    quantity = it.quantity!!
                )
            }
            .let(entryRepository::saveAll)
        notifier.notifyChanges(Request::class, request.id!!, NotifyChangeType.ADD)
        return mapper.toDto(requestRepository.findById(request.id!!).get())
    }
}
