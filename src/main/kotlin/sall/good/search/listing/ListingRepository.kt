package sall.good.search.listing

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ListingRepository: ElasticsearchRepository<ListingIndice, Long> {
}
