package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.app.entities.Request
import ru.bardinpetr.itmo.meddelivery.app.entities.RequestEntry
import ru.bardinpetr.itmo.meddelivery.app.entities.enums.TaskStatus
import ru.bardinpetr.itmo.meddelivery.app.repository.MedicalFacilityRepository
import ru.bardinpetr.itmo.meddelivery.app.repository.RequestRepository
import ru.bardinpetr.itmo.meddelivery.common.auth.service.UserService
import ru.bardinpetr.itmo.meddelivery.common.errors.NotAvailableException
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.rest.base.ICommonRestRepository
import ru.bardinpetr.itmo.meddelivery.common.ws.NotifyChangeType

@Service
class RequestService(
    private val requestRepo: RequestRepository,
    private val facilityRepo: MedicalFacilityRepository,
    private val userService: UserService,
    override val repo: ICommonRestRepository<Request>,
) : AbstractBaseService<Request>(Request::class, repo) {

    @Transactional
    fun makeOrder(entries: List<RequestEntry>): Request {
        val user = userService.getCurrent()!!
        val facility = facilityRepo.getFirstByResponsibleUserId(user.id!!)
        return Request(
            status = TaskStatus.QUEUED,
            medicalFacility = facility,
            user = user,
        )
            .apply { requestEntries.addAll(entries) }
            .let(requestRepo::save)
            .also { notifier.notifyChanges(Request::class, it.id!!, NotifyChangeType.ADD) }
    }

    override fun create(input: Request): Request = throw NotAvailableException()
    override fun update(id: IdType, patch: Request): Request = throw NotAvailableException()
}
