package ru.bardinpetr.itmo.meddelivery.common.auth.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.envers.Audited
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity

@Entity
@Audited
@Table(name = "user")
class User(
    @field:NotBlank
    var username: String,

    @field:NotBlank
    var passwordHash: String,

    @Enumerated(EnumType.STRING)
    var role: UserRole,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null
) : IBaseEntity
