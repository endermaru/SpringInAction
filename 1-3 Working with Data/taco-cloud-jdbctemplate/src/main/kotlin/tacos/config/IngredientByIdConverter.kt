package tacos.config

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tacos.data.IngredientRepository
import tacos.domain.Ingredient
import tacos.domain.Ingredient.Type

@Component
class IngredientByIdConverter(
    private val ingredientRepository: IngredientRepository
) : Converter<String, Ingredient> {

    // Converter 클래스의 convert 메서드를 override
    override fun convert(id: String): Ingredient? {
        return ingredientRepository.findById(id)
    }
}