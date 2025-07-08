package tacos.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Ingredient (
    @Id
    val id: String = "",
    var name: String = "",
    var type: Type = Type.WRAP,
) {
    enum class Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}