package sia.tacocloud.tacos

import sia.tacocloud.tacos.TacoOrder
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.bind.support.SessionStatus
import sia.tacocloud.tacos.config.TacoOrderProperties
import sia.tacocloud.tacos.data.OrderRepository
import sia.tacocloud.tacos.data.UserRepository
import java.security.Principal


@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
class OrderController(
    private val orderRepository: OrderRepository,
    private val tacoOrderProperties: TacoOrderProperties
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @GetMapping
    fun ordersForUser(
        @AuthenticationPrincipal user:User,
        model: Model,
    ): String {
        val pageable = PageRequest.of(0, tacoOrderProperties.pageSize)
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable))
        return "orderList"
    }

    @GetMapping("/current")
    fun orderForm(): String {
        return "orderForm"
    }



    @PostMapping
    fun processOrder(
        @Valid order: TacoOrder,
        errors: Errors,
        sessionStatus: SessionStatus,
    ): String {
        if (errors.hasErrors()) {
            log.info(errors.toString())
            return "orderForm";
        }
        log.info("Order submitted: {}", order)

        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as User

        order.user = user

        orderRepository.save(order)
        sessionStatus.setComplete()
        return "redirect:/"
    }
}