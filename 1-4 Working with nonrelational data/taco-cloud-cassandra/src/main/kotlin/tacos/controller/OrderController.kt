package tacos.controller

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.bind.support.SessionStatus
import tacos.data.OrderRepository
import tacos.domain.TacoOrder

@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
class OrderController(
    private val orderRepository: OrderRepository,
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    // /order/current로 리다이렉트됐을 때 "orderForm" view를 보여줌
    @GetMapping("/current")
    fun orderForm(): String {
        return "orderForm"
    }

    // orderForm에서 폼을 제출(tacoOrder 주문) 시 처리 후 홈으로 리다이렉트
    @PostMapping
    fun processOrder(
        @Valid order: TacoOrder,
        errors: Errors,
        sessionStatus: SessionStatus
    ): String {
        if (errors.hasErrors()) {
            log.info(errors.toString())
            return "orderForm";
        }
        log.info("Order submitted: {}", order)
        orderRepository.save(order)
        sessionStatus.setComplete()
        return "redirect:/"
    }
}