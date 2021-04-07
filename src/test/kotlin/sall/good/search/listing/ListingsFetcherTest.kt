package sall.good.search.listing

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import
import org.springframework.data.elasticsearch.core.geo.GeoPoint
import sall.good.search.LocalElasticsearchTestConfig
import sall.good.search.LocalIntegrationTest
import java.time.LocalDate
import java.time.LocalDateTime

internal class ListingsFetcherTest : LocalIntegrationTest() {

    @BeforeEach
    fun beforeEach() {
        indexListings(
            ListingIndice(
                name = "공덕래미안 1차 102동 301호",
                geo = GeoPoint(37.5434415, 126.9535384),
                roomCount = 3,
                bathRoomCount = 2,
                constructedDate = LocalDate.now().minusYears(10),
                address = "서울시 마포구 백범로37길 12",
                listedDateTime = LocalDateTime.now(),
                updatedDateTime = LocalDateTime.now(),
            )
        )
    }

    @Test
    fun listings() {
        val names: List<String> = dgs.executeAndExtractJsonPath(
            """
            {
                listings {
                    name
                }
            }
        """.trimIndent(), "data.listings[*].name"
        )

        assertThat(names).contains("공덕래미안 1차 102동 301호")
    }
}
