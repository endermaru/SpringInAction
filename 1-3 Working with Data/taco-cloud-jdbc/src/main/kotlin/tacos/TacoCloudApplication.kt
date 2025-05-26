package tacos

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import tacos.data.IngredientRepository
import tacos.domain.Ingredient
import tacos.domain.Ingredient.Type

@SpringBootApplication
class TacoCloudApplication

fun main(args: Array<String>) {
    runApplication<TacoCloudApplication>(*args)
}

// 둘 중 하나를 선택
@Bean
fun dataLoader(
    repo: IngredientRepository
) = CommandLineRunner {
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

//@Bean
//fun dataLoader(
//    repo: IngredientRepository,
//) = ApplicationRunner {
//    repo.save(Ingredient("FLTO", "Flour Tortilla", Type.WRAP))
//    repo.save(Ingredient("COTO", "Corn Tortilla", Type.WRAP))
//    repo.save(Ingredient("GRBF", "Ground Beef", Type.PROTEIN))
//    repo.save(Ingredient("CARN", "Carnitas", Type.PROTEIN))
//    repo.save(Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES))
//    repo.save(Ingredient("LETC", "Lettuce", Type.VEGGIES))
//    repo.save(Ingredient("CHED", "Cheddar", Type.CHEESE))
//    repo.save(Ingredient("JACK", "Monterrey Jack", Type.CHEESE))
//    repo.save(Ingredient("SLSA", "Salsa", Type.SAUCE))
//    repo.save(Ingredient("SRCR", "Sour Cream", Type.SAUCE))
//}