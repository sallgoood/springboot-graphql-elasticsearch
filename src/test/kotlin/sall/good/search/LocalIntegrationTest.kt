package sall.good.search

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.netflix.graphql.dgs.DgsQueryExecutor
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.support.WriteRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.common.xcontent.XContentType
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.core.io.Resource
import org.springframework.test.context.ActiveProfiles
import sall.good.search.listing.ListingIndice
import sall.good.search.listing.ListingRepository
import javax.annotation.PostConstruct

@SpringBootTest
@ActiveProfiles("local")
abstract class LocalIntegrationTest {

    @Autowired
    lateinit var dgs: DgsQueryExecutor

    @Value("classpath:elasticsearch/listings-mapping.json")
    lateinit var mapping: Resource

    @Value("classpath:elasticsearch/listings-setting.json")
    lateinit var setting: Resource

    @Autowired
    lateinit var elasticSearchClient: RestHighLevelClient

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var listingRepository: ListingRepository

    @BeforeEach
    fun setup() {
        createIndex()
    }

    fun createIndex() {
        val typeRef: TypeReference<Map<String, Any>> = object : TypeReference<Map<String, Any>>() {}
        val settingMap = objectMapper.readValue(setting.file, typeRef)
        val mappingMap = objectMapper.readValue(mapping.file, typeRef)

        val request = CreateIndexRequest("listings").settings(settingMap).mapping(mappingMap)
        elasticSearchClient.indices().create(request, RequestOptions.DEFAULT)
    }

    fun deleteListings() {
        elasticSearchClient.indices().delete(DeleteIndexRequest("listings"), RequestOptions.DEFAULT)
    }

    fun indexListings(vararg listings: ListingIndice) {
//        listingRepository.saveAll(listings.toList())
        listings
            .map { objectMapper.writeValueAsString(it) }
            .map {
                elasticSearchClient.index(
                    IndexRequest("listings")
                        .source(it, XContentType.JSON)
                        .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE), RequestOptions.DEFAULT
                )
            }
    }
}
