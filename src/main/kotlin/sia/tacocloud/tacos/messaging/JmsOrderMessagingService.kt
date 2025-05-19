package sia.tacocloud.tacos.messaging

import jakarta.jms.Destination
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.core.MessageCreator
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import sia.tacocloud.tacos.TacoOrder

@Service
class JmsOrderMessagingService(
    private val jms: JmsTemplate,
//    private val orderQueue: Destination
): OrderMessagingService {
    override fun sendOrder(order: TacoOrder) {
        jms.convertAndSend("tacocloud.order.queue", order)
    }
}

