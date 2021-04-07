package sall.good.search.listing

import com.example.demo.generated.DgsConstants.QUERY.Listings
import com.example.demo.generated.DgsConstants.QUERY_TYPE
import com.example.demo.generated.types.Listing
import com.example.demo.generated.types.ListingFilter
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.InputArgument
import graphql.language.Field
import graphql.language.SelectionSet

@DgsComponent
class ListingsFetcher(
    private val service: ListingService
) {

    @DgsData(parentType = QUERY_TYPE, field = Listings)
    fun listings(
        dfe: DgsDataFetchingEnvironment,
        @InputArgument("filter") filter: ListingFilter?
    ): List<Listing> {
        val fields = extractFields(null, dfe.operationDefinition.selectionSet, mutableListOf())
            .also { it.removeFirst() }
            .toTypedArray()

        return service.findListings(filter, fields).map {
            it.run {
                Listing(
                    id = id!!,
                    name = name,
                    latitude = geo?.lat,
                    longitude = geo?.lon,
                    roomCount = roomCount,
                    bathRoomCount = bathRoomCount,
                    constructedDate = constructedDate,
                    address = address,
                )
            }
        }
    }

    fun extractFields(parent: String?, set: SelectionSet, fields: MutableList<String>): MutableList<String> {
        return if (set.selections.isEmpty()) fields
        else {
            set.selections.forEach {
                if (parent.isNullOrEmpty()) fields.add((it as Field).name)
                else fields.add("$parent.${(it as Field).name}")
                if (it.selectionSet != null)
                    it.selectionSet.selections.map { inner ->
                        fields.add((inner as Field).name)
                        if (inner.selectionSet != null) {
                            extractFields(inner.name, inner.selectionSet, fields)
                        }
                    }
            }
            return fields
        }
    }
}
