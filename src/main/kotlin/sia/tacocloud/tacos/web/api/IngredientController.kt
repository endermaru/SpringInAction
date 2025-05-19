package sia.tacocloud.tacos.web.api

import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import sia.tacocloud.tacos.Ingredient
import sia.tacocloud.tacos.data.IngredientRepository

@RestController
@RequestMapping(path= ["/api/ingredients"], produces = ["application/json"])
class IngredientController(
    private val repo: IngredientRepository,
) {
    @GetMapping
    fun allIngredients(): Iterable<Ingredient> {
        return repo.findAll()
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveIngredient(@RequestBody ingredient: Ingredient): Ingredient {
        return repo.save(ingredient)
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteIngredient(@PathVariable("id") ingredientId: String) {
        repo.deleteById(ingredientId)
    }
}