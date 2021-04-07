package sall.good.search.listing

import com.example.demo.generated.types.ListingFilter
import graphql.language.Field
import graphql.language.SelectionSet
import org.elasticsearch.common.unit.DistanceUnit
import org.elasticsearch.common.unit.DistanceUnit.KILOMETERS
import org.elasticsearch.index.query.*
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Service

@Service
class ListingService(
    private val template: ElasticsearchRestTemplate,
) {
    fun findListings(filter: ListingFilter?, fields: Array<String>): List<ListingIndice> {
        val terms = filter?.terms?.map {
            it?.run {
                TermQueryBuilder(field, value)
            }
        }

        val matches = filter?.matches?.map {
            it?.run {
                MatchQueryBuilder(field, value)
            }
        }

        val ranges = filter?.ranges?.map {
            it?.run {
                RangeQueryBuilder(field).gte(gte).lte(lte)
            }
        }

        val geoDistance = filter?.geoDistance?.run {
            GeoDistanceQueryBuilder("geo")
                .point(latitude, longitude)
                .distance(distance, DistanceUnit.fromString(distanceUnit))
        }

        val query = BoolQueryBuilder()
        terms?.run { query.must().addAll(this) }
        matches?.run { query.must().addAll(this) }
        ranges?.run { query.must().addAll(this) }
        geoDistance?.run { query.must().add(this) }

        return template.search(
            NativeSearchQueryBuilder()
                .withFilter(query)
                .withFields(*fields)
                .build(), ListingIndice::class.java
        ).map { it.content }.toList()
    }
}
