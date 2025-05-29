package tacos.data

import org.springframework.data.repository.CrudRepository
import tacos.domain.TacoOrder

interface OrderRepository: CrudRepository<TacoOrder, String>