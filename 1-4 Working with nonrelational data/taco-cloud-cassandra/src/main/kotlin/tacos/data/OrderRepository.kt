package tacos.data

import org.springframework.data.repository.CrudRepository
import tacos.domain.TacoOrder
import java.util.UUID

interface OrderRepository: CrudRepository<TacoOrder, UUID>