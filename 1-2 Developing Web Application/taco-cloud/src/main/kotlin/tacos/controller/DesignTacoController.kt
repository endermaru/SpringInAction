package tacos.controller

//import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes

import tacos.domain.Ingredient
import tacos.domain.Ingredient.Type
import tacos.domain.Taco
import tacos.domain.TacoOrder

//@Slf4j - Java 방식: Lombok이 자동으로 Logger log를 생성
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
class DesignTacoController {

    // Kotlin 방식의 정적인 log 방식
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @ModelAttribute
    fun addIngredientsToModel(model: Model) {
        val ingredients = listOf<Ingredient>(
            Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
            Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
            Ingredient("CARN", "Carnitas", Type.PROTEIN),
            Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            Ingredient("LETC", "Lettuce", Type.VEGGIES),
            Ingredient("CHED", "Cheddar", Type.CHEESE),
            Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
            Ingredient("SLSA", "Salsa", Type.SAUCE),
            Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        )

        // Type을 모두 가져옴 - WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
        val types: List<Type> = Ingredient.Type.entries

        // Type 별로
        for (type in types) {
            // 모델에 데이터 추가 - 키와 값으로 구성
            model.addAttribute(
                // 키는 wrap, protein...
                type.name.lowercase(),
                // 값은 해당 type에 해당하는 재료 리스트
                filterByType(ingredients, type)
            )
        }
        // 결과
        // model["wrap"] = listOf(Ingredient("FLTO", "Flour Tortilla", Type.WRAP), ...)
        // model["protein"] = listOf(Ingredient("GRBF", "Ground Beef", Type.PROTEIN), ...)
        // model["veggies"] = listOf(Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES), ...)
    }

    @ModelAttribute(name = "tacoOrder")
    fun order(): TacoOrder {
        return TacoOrder()
    }

    @ModelAttribute(name = "taco")
    fun taco(): Taco {
        return Taco()
    }

    @GetMapping
    fun showDesignForm(): String {
        return "design"
    }

    private fun filterByType(ingredients: List<Ingredient>, type: Type): List<Ingredient> {
        return ingredients.filter { it.type == type }
    }

}