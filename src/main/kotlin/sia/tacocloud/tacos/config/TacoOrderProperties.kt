package sia.tacocloud.tacos.config

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.hibernate.annotations.Parameter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

@Component
@ConfigurationProperties(prefix="taco.orders")
@Validated
data class TacoOrderProperties(
    @param:Min(value=5, message="must be between 5 and 25")
    @param:Max(value=5, message="must be between 5 and 25")
    var pageSize:Int = 20,
)
