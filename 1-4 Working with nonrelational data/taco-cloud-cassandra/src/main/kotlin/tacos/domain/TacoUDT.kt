package tacos.domain

import org.springframework.data.cassandra.core.mapping.UserDefinedType

@UserDefinedType("taco")
data class TacoUDT(
    val name: String,
    val ingredients: MutableList<IngredientUDT>,
)