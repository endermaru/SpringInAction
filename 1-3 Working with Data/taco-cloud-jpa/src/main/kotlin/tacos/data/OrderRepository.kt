package tacos.data

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import tacos.domain.TacoOrder
import java.util.Date

interface OrderRepository: CrudRepository<TacoOrder, Long> {
    // 특정 Zip code, 특정 기간 동안 배달건을 리스트로 가져오기
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
}