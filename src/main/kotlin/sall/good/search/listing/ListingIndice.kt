package sall.good.search.listing

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Mapping
import org.springframework.data.elasticsearch.annotations.Setting
import org.springframework.data.elasticsearch.core.geo.GeoPoint
import sall.good.search.listing.elasticsearch.ElasticSearchConfig.Companion.DATETIME_FORMAT_STRING
import sall.good.search.listing.elasticsearch.ElasticSearchConfig.Companion.DATE_FORMAT
import sall.good.search.listing.elasticsearch.ElasticSearchConfig.Companion.DATE_FORMAT_STRING
import java.time.LocalDate
import java.time.LocalDateTime

@Document(indexName = "listings", createIndex = false)
data class ListingIndice(
    @Id val id: String? = null,
    val name: String? = null,
    val geo: GeoPoint? = null,
    val roomCount: Int? = null,
    val bathRoomCount: Int? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_STRING)
    val constructedDate: LocalDate? = null,
    val address: String? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT_STRING)
    val listedDateTime: LocalDateTime? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT_STRING)
    val updatedDateTime: LocalDateTime? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT_STRING)
    val unlistedDateTime: LocalDateTime? = null,
)
