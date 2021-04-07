package sall.good.search

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.client.RequestOptions.DEFAULT
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.core.io.Resource
import sall.good.search.listing.ListingIndice
import sall.good.search.listing.ListingRepository

@TestConfiguration
class LocalElasticsearchTestConfig {
}
