package tacos.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.CreditCardNumber

import java.util.Date

@Entity
@Table(name="taco_order")
class TacoOrder(
    @Id
    var id: Long = 0,
    var placedAt: Date = Date(),
    @Column(name = "DELIVERY_NAME") // optional
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
    // 일대다, 부모 엔티티에 수행한 작업을 자식에게 자동으로 전파(save 등)
    @OneToMany(cascade = [(CascadeType.ALL)])
    var tacos: MutableList<Taco> = mutableListOf()
) {
    fun addTaco(taco: Taco) {
        tacos.add(taco)
    }
}