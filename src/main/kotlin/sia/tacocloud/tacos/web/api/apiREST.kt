package sia.tacocloud.tacos.web.api

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.util.UriComponentsBuilder
import sia.tacocloud.tacos.Ingredient
import java.net.URI


class apiREST {
    val rest = restTemplate()
    val log = LoggerFactory.getLogger(apiREST::class.java)

    fun getIngredientById(ingredientId: String): Ingredient? {
        val responseEntity = rest.getForEntity(
            "http://localhost:8080/ingredients/{id}",
            Ingredient::class.java,
            ingredientId
        )
        log.info("Fetched time: {}",
            responseEntity.headers.date)
        return responseEntity.body;
    }

    fun updateIngredient(ingredient: Ingredient) {
        rest.put(
            "http://localhost:8080/ingredients/{id}",
            ingredient,
            ingredient.id
        )
    }

    fun deleteIngredient(ingredient: Ingredient) {
        rest.delete(
            "http://localhost:8080/ingredients/{id}",
            ingredient.id
        )
    }

    fun createIngredient(ingredient: Ingredient)
    : Ingredient? {
        val responseEntity = rest.postForEntity(
            "http://localhost:8080/ingredients",
            ingredient,
            Ingredient::class.java
        )
        log.info("New resource created at {}",
            responseEntity.headers.location)
        return responseEntity.body;
    }
}