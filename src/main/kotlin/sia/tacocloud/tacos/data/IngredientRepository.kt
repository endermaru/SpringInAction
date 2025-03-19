package sia.tacocloud.tacos.data

import org.springframework.data.repository.CrudRepository
import sia.tacocloud.tacos.Ingredient

interface IngredientRepository : CrudRepository<Ingredient, String> {
//    fun findAll(): List<Ingredient>
//    fun findById(id: String): Ingredient?
//    fun save(ingredient: Ingredient): Ingredient
}