package ru.bardinpetr.itmo.meddelivery.app.entities.product

import jakarta.persistence.*
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
data class RequestEntry(
    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    var request: Request?,

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    var productType: ProductType,

    @Column(nullable = false)
    var quantity: Int,

    @Column(nullable = false)
    var fulfilledQuantity: Int = 0,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity
