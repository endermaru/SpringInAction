package sia.tacocloud.tacos.config

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import sia.tacocloud.tacos.Ingredient
import sia.tacocloud.tacos.Ingredient.Type
import sia.tacocloud.tacos.Taco
import sia.tacocloud.tacos.data.IngredientRepository
import sia.tacocloud.tacos.data.TacoRepository
import sia.tacocloud.tacos.data.UserRepository
import java.util.*


@Configuration
class DataLoader {
    @Bean
    fun dataLoaderREST(
        repo: IngredientRepository,
        userRepo: UserRepository,
        encoder: PasswordEncoder,
        tacoRepo: TacoRepository
    ) = CommandLineRunner {
        val flourTortilla = Ingredient(
            "FLTO", "Flour Tortilla", Type.WRAP
        )
        val cornTortilla = Ingredient(
            "COTO", "Corn Tortilla", Type.WRAP
        )
        val groundBeef = Ingredient(
            "GRBF", "Ground Beef", Type.PROTEIN
        )
        val carnitas = Ingredient(
            "CARN", "Carnitas", Type.PROTEIN
        )
        val tomatoes = Ingredient(
            "TMTO", "Diced Tomatoes", Type.VEGGIES
        )
        val lettuce = Ingredient(
            "LETC", "Lettuce", Type.VEGGIES
        )
        val cheddar = Ingredient(
            "CHED", "Cheddar", Type.CHEESE
        )
        val jack = Ingredient(
            "JACK", "Monterrey Jack", Type.CHEESE
        )
        val salsa = Ingredient(
            "SLSA", "Salsa", Type.SAUCE
        )
        val sourCream = Ingredient(
            "SRCR", "Sour Cream", Type.SAUCE
        )

        repo.save(flourTortilla)
        repo.save(cornTortilla)
        repo.save(groundBeef)
        repo.save(carnitas)
        repo.save(tomatoes)
        repo.save(lettuce)
        repo.save(cheddar)
        repo.save(jack)
        repo.save(salsa)
        repo.save(sourCream)
        val taco1 = Taco()
        taco1.name = "Carnivore"
        taco1.ingredients = mutableListOf(
            flourTortilla, groundBeef, carnitas,
            sourCream, salsa, cheddar
        )
        tacoRepo.save(taco1)
        val taco2 = Taco()
        taco2.name = "Bovine Bounty"
        taco2.ingredients = mutableListOf(
            cornTortilla, groundBeef, cheddar,
            jack, sourCream
        )
        tacoRepo.save(taco2)
        val taco3 = Taco()
        taco3.name = "Veg-Out"
        taco3.ingredients = mutableListOf(
            flourTortilla, cornTortilla, tomatoes,
            lettuce, salsa
        )
        tacoRepo.save(taco3)
    }
}