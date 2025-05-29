package tacos.config

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tacos.data.IngredientRepository
import tacos.domain.Ingredient
import tacos.domain.Ingredient.Type
import tacos.domain.IngredientUDT
import tacos.domain.TacoUDRUtils

@Component
class IngredientByIdConverter(
    private val ingredientRepo: IngredientRepository
) : Converter<String, IngredientUDT> {
    // Converter 클래스의 convert 메서드를 override
    override fun convert(id: String): IngredientUDT? {
        val ingredient = ingredientRepo.findById(id).orElse(null)
        return ingredient?.let { TacoUDRUtils.toIngredientUDT(it) }
    }
}