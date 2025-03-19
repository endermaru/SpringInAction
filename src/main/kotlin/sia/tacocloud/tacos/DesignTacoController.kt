package sia.tacocloud.tacos


import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import sia.tacocloud.tacos.Ingredient.Type
import sia.tacocloud.tacos.data.IngredientRepository

@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
class DesignTacoController(
    private val ingredientRepository: IngredientRepository,
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)  // ✅ 코틀린 스타일
    }

    @ModelAttribute
    fun addIngredientsToModel(model: Model) {
//        val ingredients = listOf<Ingredient>(
//            Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//            Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//            Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//            Ingredient("CARN", "Carnitas", Type.PROTEIN),
//            Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//            Ingredient("LETC", "Lettuce", Type.VEGGIES),
//            Ingredient("CHED", "Cheddar", Type.CHEESE),
//            Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//            Ingredient("SLSA", "Salsa", Type.SAUCE),
//            Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//        )
        val ingredients = ingredientRepository.findAll()

        // Type을 모두 가져옴 - WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
        val types: List<Type> = Ingredient.Type.entries
        // Type 별로
        for (type in types) {
            // 모델에 데이터 추가 - 키와 값으로 구성
            model.addAttribute(
                // 키는 wrap, protein...
                type.name.lowercase(),
                // 값은 해당 type에 해당하는 재료 리스트
                filterByType(ingredients.toList(), type)
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
        @Valid taco:Taco,
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