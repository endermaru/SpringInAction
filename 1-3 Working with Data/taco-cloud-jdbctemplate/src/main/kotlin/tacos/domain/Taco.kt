package tacos.domain

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.util.Date

data class Taco(
    var id: Long = 0,
    var createdAt: Date = Date(),

    @field:NotBlank(message = "Name cannot be empty")
    @field:Size(min=5, message="Name must be at least 5 characters long")
    var name: String = "",

    @field:NotEmpty(message = "You must choose at least 1 ingredient")
    var ingredients: MutableList<IngredientRef> = mutableListOf(),
)
