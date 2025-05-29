package tacos.domain

import com.datastax.oss.driver.api.core.uuid.Uuids
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.CreditCardNumber
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

import java.util.Date
import java.util.UUID

@Table("orders")
data class TacoOrder (
    val serialVersionUID: Long = 1L,

    @PrimaryKey
    val id: UUID = Uuids.timeBased(),

    var placedAt: Date = Date(),

    @field: NotBlank(message="Delivery name is required")
    var deliveryName: String = "",
    @field: NotBlank(message="Delivery Street is required")
    var deliveryStreet: String = "",
    @field: NotBlank(message="Delivery City is required")
    var deliveryCity: String = "",
    @field: NotBlank(message="Delivery State is required")
    @field: Size(min=2, max=2, message="Delivery State must be two letters")
    var deliveryState: String = "",
    @field:NotBlank(message="Zip code is required")
    var deliveryZip: String = "",
    @field:CreditCardNumber(message="Not a valid credit card number")
    var ccNumber: String = "",
    @field:Pattern(regexp="^(0[1-9]|1[0-2])([/])([2-9][0-9])$",
        message="Must be formatted MM/YY")
    var ccExpiration: String = "",
    @field:Digits(integer=3, fraction=0, message="Invalid CVV")
    var ccCVV: String = "",
    @Column("tacos")
    var tacos: MutableList<TacoUDT> = mutableListOf()
) {
    fun addTaco(taco: TacoUDT) {
        tacos.add(taco)
    }
}