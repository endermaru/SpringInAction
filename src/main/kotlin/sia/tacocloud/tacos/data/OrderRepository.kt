package sia.tacocloud.tacos.data

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import sia.tacocloud.tacos.TacoOrder
import sia.tacocloud.tacos.User
import java.util.*

interface OrderRepository: CrudRepository<TacoOrder, Long> {
    fun readOrdersByDeliveryZipAndPlacedAtBetween(
        deliveryZipCode: String,
        startDate: Date,
        endDate: Date,
    ) : List<TacoOrder>

    // DeliveryName, DeliveryCity 에 대해 대소문자 무시하고 동일한 데이터 찾기
    fun findByDeliveryNameIgnoreCaseAndDeliveryCityIgnoreCase(
        deliveryName: String,
        deliveryCity: String,
    ) : List<TacoOrder>

    // DeliveryCity 와 일치하는 데이터를 Name 오름차순으로 정렬
    fun findByDeliveryCityOrderByDeliveryName(
        city: String,
    )

    // 특정 JPQL을 이용한 커스텀 함수
    @Query("SELECT o FROM TacoOrder o WHERE o.deliveryCity = 'Seattle'")
    fun readOrdersDeliveredInSeattle(): List<TacoOrder>

    fun findByUserOrderByPlacedAtDesc(user: User, pageable: Pageable):List<TacoOrder>
}