package tacos.domain

import java.util.stream.Collectors

object TacoUDRUtils {
    fun toTacoUDT(taco: Taco): TacoUDT {
        return TacoUDT(taco.name, taco.ingredients)
    }

    fun toIngredientUDTs(ingredients: MutableList<Ingredient>): MutableList<IngredientUDT> {
        return ingredients.stream()
            .map { ingredient -> toIngredientUDT(ingredient) }
            .collect(Collectors.toList())
    }

    fun toIngredientUDT(ingredient: Ingredient): IngredientUDT {
        return IngredientUDT(ingredient.name, ingredient.type)
    }
}