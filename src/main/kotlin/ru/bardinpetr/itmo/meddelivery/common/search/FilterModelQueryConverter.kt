package ru.bardinpetr.itmo.meddelivery.common.search

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.util.*

/**
 * Supports serialization as:
 * - string of constraints "$field#$operator#$value" delimited with ","
 * - base64 JSON of key-value pair for equality check
 */
@Component
class FilterModelQueryConverter : Converter<String, FilterModel> {
    private val objectMapper = jacksonObjectMapper()

    override fun convert(source: String) =
        source
            .runCatching(::convertJSON).getOrNull()
            ?: convertClassic(source)

    private fun convertJSON(source: String) =
        Base64.getDecoder()
            .decode(source)
            .let { objectMapper.readValue<List<FilterConstraintModel>>(it) }
            .let(::FilterModel)

    private fun convertClassic(source: String) =
        FilterModel(
            source
                .split(",")
                .mapNotNull {
                    val parts = it.split("#")
                    if (parts.size != 3) {
                        null
                    } else {
                        FilterConstraintModel(
                            field = parts[0],
                            operator = FilterOperator.valueOf(parts[1]),
                            value = parts[2],
                            type = "string"
                        )
                    }
                }
        )
}
