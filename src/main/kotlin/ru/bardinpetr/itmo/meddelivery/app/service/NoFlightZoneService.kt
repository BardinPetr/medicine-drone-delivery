
package ru.bardinpetr.itmo.meddelivery.app.service

import org.springframework.stereotype.Service
import ru.bardinpetr.itmo.meddelivery.app.entities.geo.NoFlightZone
import ru.bardinpetr.itmo.meddelivery.common.rest.base.AbstractBaseService

@Service
class NoFlightZoneService : AbstractBaseService<NoFlightZone>(NoFlightZone::class)
