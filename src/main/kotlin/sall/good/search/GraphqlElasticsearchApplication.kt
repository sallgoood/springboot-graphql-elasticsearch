package sall.good.search

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraphqlElasticsearchApplication

fun main(args: Array<String>) {
	runApplication<GraphqlElasticsearchApplication>(*args)
}
