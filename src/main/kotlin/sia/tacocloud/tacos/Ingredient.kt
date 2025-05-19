package sia.tacocloud.tacos

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import java.io.Serializable

@Entity
class Ingredient(
    @Id
    var id: String = "",
    var name: String = "",
    @Enumerated(EnumType.STRING)
    var type: Type = Type.WRAP,
) : Serializable {
    enum class Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
