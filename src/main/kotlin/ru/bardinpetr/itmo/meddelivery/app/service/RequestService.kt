package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.app.entities.Request
import ru.bardinpetr.itmo.meddelivery.app.entities.RequestEntry
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.TaskStatus
import ru.bardinpetr.itmo.meddelivery.app.models.RequestEntryModel
import ru.bardinpetr.itmo.meddelivery.app.repository.MedicalFacilityRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.RequestRepository
import ru.bardinpetr.itmo.meddelivery.app.service.drone.FlightPlannerService
import ru.bardinpetr.itmo.meddelivery.common.auth.service.UserService
import ru.bardinpetr.itmo.meddelivery.common.base.repo.ICommonRestRepository
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.errors.NotAvailableException
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.utils.error.notFound
import ru.bardinpetr.itmo.meddelivery.common.utils.logger

@Service
class RequestService(
    private val requestRepo: RequestRepository,
    private val facilityRepo: MedicalFacilityRepository,
    private val userService: UserService,
    private val plannerService: FlightPlannerService,
    private val pTypeService: ProductTypeService,
    override val repo: ICommonRestRepository<Request>,
) : AbstractBaseService<Request>(Request::class, repo) {

    private val log = logger<RequestService>()

    private fun resolveRequestEntry(entry: RequestEntryModel, request: Request) = RequestEntry(
        productType = pTypeService.getByCode(entry.productTypeCode) ?: throw notFound(),
        quantity = entry.quantity,
        request = request
    )

    @Transactional
    fun makeOrder(entries: List<RequestEntryModel>): Request {
        val user = userService.getCurrent()!!
        val facility = facilityRepo.getFirstByResponsibleUserId(user.id!!)
        log.info("New MF=${facility?.name}: ${entries.joinToString(", ") { "${it.productTypeCode}@${it.quantity}" }}")
        return Request(
            status = TaskStatus.QUEUED,
            medicalFacility = facility,
            user = user,
        )
            .let(requestRepo::save)
            .apply { requestEntries!!.addAll(entries.map { resolveRequestEntry(it, this) }) }
            .let(requestRepo::save)
            .also { plannerService.processUnfulfilledRequests() }
    }

    override fun create(input: Request): Request = throw NotAvailableException()
    override fun update(id: IdType, patch: Request): Request = throw NotAvailableException()
}
