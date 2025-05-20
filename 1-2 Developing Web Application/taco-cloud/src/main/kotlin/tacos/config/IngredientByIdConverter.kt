package tacos.config

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tacos.domain.Ingredient
import tacos.domain.Ingredient.Type

@Component
class IngredientByIdConverter : Converter<String, Ingredient> {
    // id 문자열 - Ingredient 객체 매핑 테이블
    private val ingredientMap: MutableMap<String, Ingredient> = HashMap()

    // 생성자 - 앱 시작 시 한 번만 실행되어 매핑 테이블 등록
    init {
        ingredientMap["FLTO"] = Ingredient("FLTO", "Flour Tortilla", Type.WRAP)
        ingredientMap["COTO"] = Ingredient("COTO", "Corn Tortilla", Type.WRAP)
        ingredientMap["GRBF"] = Ingredient("GRBF", "Ground Beef", Type.PROTEIN)
        ingredientMap["CARN"] = Ingredient("CARN", "Carnitas", Type.PROTEIN)
        ingredientMap["TMTO"] = Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES)
        ingredientMap["LETC"] = Ingredient("LETC", "Lettuce", Type.VEGGIES)
        ingredientMap["CHED"] = Ingredient("CHED", "Cheddar", Type.CHEESE)
        ingredientMap["JACK"] = Ingredient("JACK", "Monterrey Jack", Type.CHEESE)
        ingredientMap["SLSA"] = Ingredient("SLSA", "Salsa", Type.SAUCE)
        ingredientMap["SRCR"] = Ingredient("SRCR", "Sour Cream", Type.SAUCE)
    }

    // Converter 클래스의 convert 메서드를 override
    override fun convert(id: String): Ingredient? {
        return ingredientMap[id]
    }
}