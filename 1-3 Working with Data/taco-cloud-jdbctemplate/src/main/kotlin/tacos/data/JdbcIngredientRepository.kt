package tacos.data

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import tacos.domain.Ingredient
import java.sql.ResultSet

@Repository
class JdbcIngredientRepository(
    private val jdbcTemplate: JdbcTemplate
): IngredientRepository {

    override fun findAll(): List<Ingredient> {
        return jdbcTemplate.query(
            "SELECT id, name, type FROM Ingredient",
            this::mapRowToIngredient
        )
    }

    override fun findById(id: String): Ingredient? {
        return jdbcTemplate.query(
            "SELECT id, name, type FROM Ingredient WHERE id=?",
            this::mapRowToIngredient,
            id
        ).firstOrNull()
    }

    override fun save(ingredient: Ingredient): Ingredient {
        jdbcTemplate.update(
            "INSERT INTO Ingredient (id, name, type) VALUES (?, ?, ?)",
            ingredient.id,
            ingredient.name,
            ingredient.type.toString()
        )
        return ingredient
    }

    // JDBC ResultSet의 한 행을 Ingredient 객체로 변환
    // 함수로 정의 -> 함수 참조(this::mapRowToIngredient) 필요
    private fun mapRowToIngredient(row: ResultSet, rowNum: Int): Ingredient {
        return Ingredient(
            id = row.getString("id"),
            name = row.getString("name"),
            type = Ingredient.Type.valueOf(row.getString("type"))
        )
    }
}