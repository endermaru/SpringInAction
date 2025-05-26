package tacos.data

import org.springframework.data.repository.CrudRepository
import tacos.domain.Ingredient

interface IngredientRepository: CrudRepository<Ingredient, String> {
    // CRUD Repository는 아래의 메서드를 기본적으로 제공하기 때문에 따로 정의할 필요가 없음
    // fun findAll(): List<Ingredient>
    // fun findById(id: String): Ingredient?
    // fun save(ingredient: Ingredient): Ingredient
}