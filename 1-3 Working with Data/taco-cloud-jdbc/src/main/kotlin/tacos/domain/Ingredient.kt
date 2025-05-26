package tacos.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
//@AllArgsConstructor - 모든 필드를 포함하는 생성자를 자동으로 생성하는 Lombok annotation
//  Kotlin의 data class는 필요 없음
//@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
//  기본 생성자(파라미터가 없는 생성자)를 자동 생성하는 Lombok 어노테이션.
//  생성자를 private으로 설정하여 외부에서 직접 호출하지 못하도록 함.
//  force = true → final 필드가 있을 경우 강제로 null 또는 기본값으로 초기화.
//  역시 Kotlin의 data class는 필요 없음
data class Ingredient(
    @Id
    var id: String = "",
    var name: String = "",
    var type: Type = Type.WRAP,
) {
    enum class Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
