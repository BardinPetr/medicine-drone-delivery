package ru.bardinpetr.itmo.meddelivery.common.rest.search

data class FilterConstraintModel(
    val field: String,
    val value: String,
    val operator: FilterOperator = FilterOperator.EQ,
    val type: String?
)

enum class FilterOperator {
    EQ, SCT, DEQ
}
