package sall.good.search.listing.elasticsearch

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_DATE
import java.time.format.DateTimeFormatter.ISO_DATE_TIME


@Configuration
class ElasticSearchConfig {

    @Bean
    fun elasticsearchCustomConversions(): ElasticsearchCustomConversions {
        return ElasticsearchCustomConversions(listOf(LocalDateTimeToString(), StringToLocalDateTime()))
    }

    @WritingConverter
    class LocalDateTimeToString : Converter<LocalDateTime, String> {
        override fun convert(source: LocalDateTime): String {
            return source.format(DATETIME_FORMAT)
        }
    }

    @ReadingConverter
    class StringToLocalDateTime : Converter<String, LocalDateTime> {
        override fun convert(source: String): LocalDateTime {
            return LocalDateTime.parse(source, DATETIME_FORMAT)
        }
    }

    @WritingConverter
    class LocalDateToString : Converter<LocalDate, String> {
        override fun convert(source: LocalDate): String {
            return source.format(DATE_FORMAT)
        }
    }

    @ReadingConverter
    class StringToLocalDate : Converter<String, LocalDate> {
        override fun convert(source: String): LocalDate {
            return LocalDate.parse(source, DATE_FORMAT)
        }
    }

    companion object {
        const val DATETIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss"
        const val DATE_FORMAT_STRING = "yyyy-MM-dd"
        val DATETIME_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT_STRING)
        val DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_STRING)
    }
}
