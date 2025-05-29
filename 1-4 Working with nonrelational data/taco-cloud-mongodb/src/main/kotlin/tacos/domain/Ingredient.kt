package tacos.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

//@Document("ingredients") //문서 이름 직접 지정 가능
@Document
data class Ingredient (
    @Id
    val id: String = "",
    var name: String = "",
    var type: Type = Type.WRAP,
) {
    enum class Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}