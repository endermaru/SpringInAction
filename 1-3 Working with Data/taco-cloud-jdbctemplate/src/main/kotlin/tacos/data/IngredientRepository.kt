package tacos.data

import tacos.domain.Ingredient

interface IngredientRepository {
    fun findAll(): List<Ingredient>
    //fun findById(id: String): Optional<Ingredient>
    // null을 직접 다루는 대신 Optional을 사용하는 Java 방식
    // Kotlin은 null을 직접 다루지만,
    // ?.(Safe Call)과 ?:(Elvis 연산자) 사용
    fun findById(id: String): Ingredient?
    fun save(ingredient: Ingredient): Ingredient
}