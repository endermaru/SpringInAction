package sia.tacocloud.tacos.config

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import sia.tacocloud.tacos.data.IngredientRepository
import sia.tacocloud.tacos.Ingredient
import sia.tacocloud.tacos.Ingredient.Type

@Configuration
class DataLoader {
    @Bean
    fun ingredientDataLoader(
        repo: IngredientRepository,
    ) = ApplicationRunner {
        repo.save(Ingredient("FLTO", "Flour Tortilla", Type.WRAP))
        repo.save(Ingredient("COTO", "Corn Tortilla", Type.WRAP))
        repo.save(Ingredient("GRBF", "Ground Beef", Type.PROTEIN))
        repo.save(Ingredient("CARN", "Carnitas", Type.PROTEIN))
        repo.save(Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES))
        repo.save(Ingredient("LETC", "Lettuce", Type.VEGGIES))
        repo.save(Ingredient("CHED", "Cheddar", Type.CHEESE))
        repo.save(Ingredient("JACK", "Monterrey Jack", Type.CHEESE))
        repo.save(Ingredient("SLSA", "Salsa", Type.SAUCE))
        repo.save(Ingredient("SRCR", "Sour Cream", Type.SAUCE))
    }
}