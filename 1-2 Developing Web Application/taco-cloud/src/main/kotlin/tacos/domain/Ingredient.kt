package tacos.domain

// Kotlin 방식 - data class 사용
data class Ingredient(
    var id: String = "",
    var name: String = "",
    var type: Type = Type.WRAP,
) {
    enum class Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}

/* Java 방식 - Lombok 사용
import lombok.Data;
@Data
public class Ingredient {
	private final String id;
	private final String name;
	private final Type type;
	public enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
}
 */