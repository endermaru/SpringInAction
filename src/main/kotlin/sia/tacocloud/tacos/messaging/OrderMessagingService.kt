package sia.tacocloud.tacos.messaging

import sia.tacocloud.tacos.TacoOrder

interface OrderMessagingService {
    fun sendOrder(order: TacoOrder)
}