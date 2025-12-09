
package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.product.RequestEntry
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService

@Service
class RequestEntryService : AbstractBaseService<RequestEntry>(RequestEntry::class)
