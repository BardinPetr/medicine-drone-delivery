package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.app.entities.Request
import ru.bardinpetr.itmo.meddelivery.app.entities.RequestEntry
import ru.bardinpetr.itmo.meddelivery.app.repository.RequestEntryRepository
import ru.bardinpetr.itmo.meddelivery.common.base.service.AbstractBaseService
import ru.bardinpetr.itmo.meddelivery.common.models.IdType
import ru.bardinpetr.itmo.meddelivery.common.utils.logger
import kotlin.jvm.optionals.getOrNull

@Service
class RequestEntryService(
    override val repo: RequestEntryRepository
) : AbstractBaseService<RequestEntry>(RequestEntry::class, repo) {

    private val log = logger<RequestEntryService>()

    fun getUnfulfilledRequests(): Map<Request, List<RequestEntry>> =
        repo.findAllUnfulfilled().groupBy { it.request!! }

    @Transactional
    fun entryIncreaseFulfilledQuantity(requestEntryId: IdType, byAmount: Int) {
        repo.findById(requestEntryId)
            .getOrNull()
            ?.also {
                log.debug("[RE=${it.id}] Ff-quantity ${it.fulfilledQuantity} -> ${it.fulfilledQuantity + byAmount}")
            }
            ?.let { repo.save(it.copy(fulfilledQuantity = it.fulfilledQuantity + byAmount)) }
    }
}
