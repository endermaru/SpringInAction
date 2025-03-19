package sia.tacocloud.tacos


import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.CreditCardNumber
import jakarta.persistence.*
import java.io.Serializable

import java.util.*

@Entity
@Table(name = "Taco_Order")
class TacoOrder (
    val serialVersionUID: Long = 1L,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    //var id:Long = 0L,

    var placedAt: Date = Date(),

    @field:NotBlank(message="Delivery name is required")
    var deliveryName: String = "",
    @field:NotBlank(message="Street is required")
    var deliveryStreet: String = "",
    @field:NotBlank(message="City is required")
    var deliveryCity: String = "",
    @field:NotBlank(message="State is required")
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

    @OneToMany(cascade = [(CascadeType.ALL)])
    var tacos: MutableList<Taco> = mutableListOf(),

    @ManyToOne
    var user: User? = null,
) : Serializable {
    fun addTaco(taco: Taco) {
        this.tacos.add(taco)
    }
}
