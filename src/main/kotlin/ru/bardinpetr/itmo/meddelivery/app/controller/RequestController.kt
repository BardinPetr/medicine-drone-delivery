package ru.bardinpetr.itmo.meddelivery.app.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bardinpetr.itmo.meddelivery.app.dto.RequestDto
import ru.bardinpetr.itmo.meddelivery.app.entities.drones.TaskStatus
import ru.bardinpetr.itmo.meddelivery.app.entities.product.Request
import ru.bardinpetr.itmo.meddelivery.app.entities.product.RequestEntry
import ru.bardinpetr.itmo.meddelivery.app.repository.MedicalFacilityRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.ProductTypeRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.RequestEntryRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.RequestRepository
import ru.bardinpetr.itmo.meddelivery.common.rest.controller.AbstractCommonRestController
import ru.bardinpetr.itmo.meddelivery.common.ws.NotifyChangeType

@RequestMapping("/api/request")
@RestController
class RequestController(
    val mfRepo: MedicalFacilityRepository,
    val requestRepository: RequestRepository,
    val productTypeRepository: ProductTypeRepository,
    val entryRepository: RequestEntryRepository
) : AbstractCommonRestController<Request, RequestDto>(Request::class) {

    override fun preUpdateHook(old: Request, next: Request) {
        throw IllegalStateException("Should not be updated")
    }

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
