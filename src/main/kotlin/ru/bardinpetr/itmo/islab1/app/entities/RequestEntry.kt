package ru.bardinpetr.itmo.islab1.app.entities

import jakarta.persistence.*
import ru.bardinpetr.itmo.islab1.common.models.IBaseEntity

@Entity
data class RequestEntry(
    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    val request: Request,

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    val productType: ProductType,

    @Column(nullable = false)
    val quantity: Int,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity