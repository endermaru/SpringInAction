package tacos.security

import jakarta.transaction.Transactional
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import tacos.data.OrderRepository
import tacos.domain.TacoOrder

@Service
class AdminService(
    private val orderRepository: OrderRepository,
) {
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteAllOrders() {
        orderRepository.deleteAll()
    }

    @Transactional
    @PostAuthorize("hasRole('ADMIN') || " +
            "returnObject.user.username == authentication.name")
    fun getOrder(id: Long): TacoOrder? {
        return orderRepository.findById(id).orElse(null)
    }
}