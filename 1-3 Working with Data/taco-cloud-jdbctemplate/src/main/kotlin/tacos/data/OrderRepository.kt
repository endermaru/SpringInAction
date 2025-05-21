package tacos.data

import tacos.domain.TacoOrder

interface OrderRepository {
    fun save(order: TacoOrder): TacoOrder
}