package tacos.domain

import com.datastax.oss.driver.api.core.uuid.Uuids
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.data.cassandra.core.cql.Ordering
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.util.Date
import java.util.UUID

@Table("tacos")
data class Taco(
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    val id: UUID = Uuids.timeBased(),

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    val createdAt: Date = Date(),

    @field:NotBlank(message = "Name cannot be empty")
    @field:Size(min=5, message="Name must be at least 5 characters long")
    var name:String = "",

    @field:NotEmpty(message = "You must choose at least 1 ingredient")
    @Column("ingredients")
    var ingredients: MutableList<IngredientUDT> = mutableListOf(),
) {
    fun addIngredient(ingredient: Ingredient) {
        ingredients.add(TacoUDRUtils.toIngredientUDT(ingredient))
    }
}