package tacos.domain

import org.springframework.data.cassandra.core.mapping.UserDefinedType

@UserDefinedType("ingredient")
data class IngredientUDT (
    val name: String,
    val type: Ingredient.Type,
)