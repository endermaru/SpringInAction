package tacos.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.util.Date

@Entity
class Taco(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    var createdAt: Date = Date(),

    @field:NotBlank(message = "Name cannot be empty")
    @field:Size(min=5, message="Name must be at least 5 characters long")
    var name:String = "",

    @field:NotEmpty(message = "You must choose at least 1 ingredient")
    @ManyToMany()
    var ingredients: MutableList<Ingredient> = mutableListOf(),
) {
    fun addIngredient(ingredient: Ingredient) {
        ingredients.add(ingredient)
    }
}