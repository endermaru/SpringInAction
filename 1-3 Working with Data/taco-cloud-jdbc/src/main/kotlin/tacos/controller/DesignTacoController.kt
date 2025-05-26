package tacos.controller

//import lombok.extern.slf4j.Slf4j
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes
import tacos.data.IngredientRepository

import tacos.domain.Ingredient
import tacos.domain.Ingredient.Type
import tacos.domain.Taco
import tacos.domain.TacoOrder

//@Slf4j - Java 방식: Lombok이 자동으로 Logger log를 생성
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
class DesignTacoController(
    private val ingredientRepository: IngredientRepository,
) {

    // Kotlin 방식의 정적인 log 방식
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @ModelAttribute
    fun addIngredientsToModel(model: Model) {
        val ingredients: List<Ingredient> = ingredientRepository.findAll().toList()
        val types: List<Type> = ingredients.map { it.type }

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

    @PostMapping
    fun processTaco(
        @ModelAttribute @Valid taco:Taco,
        errors: Errors,
        @ModelAttribute tacoOrder: TacoOrder
    ): String {
        if (errors.hasErrors()) {
            log.info(errors.toString())
            return "design";
        }
        tacoOrder.addTaco(taco)
        log.info("Processing taco: $taco")
        return "redirect:/orders/current"
    }

}