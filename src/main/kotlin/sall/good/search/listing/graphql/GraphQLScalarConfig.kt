package sall.good.search.listing.graphql

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsRuntimeWiring
import graphql.scalars.ExtendedScalars.*
import graphql.schema.idl.RuntimeWiring

@DgsComponent
class GraphQLScalarConfig {

    @DgsRuntimeWiring
    fun addDateTimeScalar(builder: RuntimeWiring.Builder): RuntimeWiring.Builder {
        return builder.scalar(DateTime)
    }

    @DgsRuntimeWiring
    fun addDateScalar(builder: RuntimeWiring.Builder): RuntimeWiring.Builder {
        return builder.scalar(Date)
    }

    @DgsRuntimeWiring
    fun addBigDecimalScalar(builder: RuntimeWiring.Builder): RuntimeWiring.Builder {
        return builder.scalar(GraphQLBigDecimal)
    }
}
