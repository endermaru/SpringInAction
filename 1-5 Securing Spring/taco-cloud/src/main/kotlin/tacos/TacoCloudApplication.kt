package tacos

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import tacos.data.IngredientRepository
import tacos.data.UserRepository
import tacos.domain.Ingredient
import tacos.domain.Ingredient.Type
import tacos.domain.User

@SpringBootApplication
class TacoCloudApplication {
    // 둘 중 하나를 선택
    @Bean
    fun dataLoader(
        repo: IngredientRepository,
        userRepo: UserRepository,
        @Lazy passwordEncoder: PasswordEncoder,
        @Value("\${custom.admin-password}") adminPassword: String,
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
        userRepo.save(User(
            username="admin",
            password=passwordEncoder.encode(adminPassword),
            role= listOf(
                SimpleGrantedAuthority("ROLE_ADMIN"),
                SimpleGrantedAuthority("ROLE_USER")
            )
        ))
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
}

fun main(args: Array<String>) {
    runApplication<TacoCloudApplication>(*args)
}
