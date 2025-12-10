package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.Request
import ru.bardinpetr.itmo.meddelivery.app.entities.RequestEntry
import ru.bardinpetr.itmo.meddelivery.app.repository.RequestEntryRepository
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService

@Service
class RequestEntryService(
    override val repo: RequestEntryRepository
) : AbstractBaseService<RequestEntry>(RequestEntry::class, repo) {

    fun getUnfulfilledRequests(): Map<Request, List<RequestEntry>> =
        repo.findAllUnfulfilled().groupBy { it.request!! } // TODO check
}
