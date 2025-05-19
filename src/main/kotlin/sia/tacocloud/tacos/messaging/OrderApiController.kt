package sia.tacocloud.tacos.messaging

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import sia.tacocloud.tacos.TacoOrder
import sia.tacocloud.tacos.data.OrderRepository

@RestController
@RequestMapping("/api/orders", produces = ["application/json"])
@CrossOrigin(origins = ["http://localhost:8080"])
class OrderApiController(
    private val repo: OrderRepository,
    private val messageService: OrderMessagingService
) {
    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun postOrder(
        @RequestBody order: TacoOrder,
    ) : TacoOrder {
        messageService.sendOrder(order)
        return repo.save(order)
    }

}