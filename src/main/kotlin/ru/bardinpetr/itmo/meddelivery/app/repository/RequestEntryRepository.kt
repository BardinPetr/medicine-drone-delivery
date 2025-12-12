package ru.bardinpetr.itmo.meddelivery.app.repository

import org.springframework.data.jpa.repository.Query
import ru.bardinpetr.itmo.meddelivery.app.entities.RequestEntry
import ru.bardinpetr.itmo.meddelivery.common.base.repo.ICommonRestRepository

interface RequestEntryRepository : ICommonRestRepository<RequestEntry> {
    @Query("select re from RequestEntry re where re.fulfilledQuantity < re.quantity")
    fun findAllUnfulfilled(): List<RequestEntry>
}
