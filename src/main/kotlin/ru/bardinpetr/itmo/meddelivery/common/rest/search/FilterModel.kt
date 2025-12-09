@file:Suppress("UNCHECKED_CAST")

package ru.bardinpetr.itmo.meddelivery.common.rest.search

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class FilterModel(
    val constraints: List<FilterConstraintModel>
) {
    fun <T : Any> asSpec(): Specification<T> =
        Specification { root: Root<T>, _: CriteriaQuery<*>?, builder: CriteriaBuilder ->
            constraints
                .map {
                    val field = resolveField(root, it.field)
                    return@map when (it.operator) {
                        FilterOperator.EQ ->
                            builder.equal(field, it.value)

                        FilterOperator.SCT ->
                            builder.like(builder.lower(field as Path<String>), "%${it.value.lowercase()}%")

                        FilterOperator.DEQ -> {
                            val date = LocalDateTime.parse(it.value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                            builder.equal(field, date)
                        }
                    }
                }
                .let { builder.and(*it.toTypedArray()) }
        }
}

private fun resolveField(root: Root<*>, path: String): Path<Any> {
    var src: Path<Any> = root as Path<Any>
    path
        .split(".")
        .forEach { src = src[it] }
    return src
}
