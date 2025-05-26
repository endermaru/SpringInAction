package tacos.data

import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.PreparedStatementCreatorFactory
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import tacos.domain.IngredientRef
import tacos.domain.Taco
import tacos.domain.TacoOrder
import java.sql.Types
import java.util.Date

@Repository
class JdbcOrderRepository (
    // JdbcOperations는 인터페이스, JdbcTemplate은 구현체
    private val jdbcOperations: JdbcOperations
): OrderRepository {
    @Transactional
    override fun save(
        order: TacoOrder
    ) : TacoOrder {
        // 1. PreparedStatementCreatorFactory: insert 쿼리와 각 파라미터의 SQL 타입을 정의한 템플릿
        val pscf = PreparedStatementCreatorFactory(
            "insert into Taco_Order "
                    + "(delivery_name, delivery_street, delivery_city, "
                    + "delivery_state, delivery_zip, cc_number, "
                    + "cc_expiration, cc_cvv, placed_at) "
                    + "values (?,?,?,?,?,?,?,?,?)",
            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
            Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP,
        )
        pscf.setReturnGeneratedKeys(true) // setReturnGeneratedKeys(true)로 생성된 id를 받아오도록 설정

        // 2. PreparedStatementCreator: 위에서 정의한 쿼리 템플릿에 실제 값을 채워 PreparedStatement 생성
        order.placedAt = Date()
        val psc = pscf.newPreparedStatementCreator(
            listOf(
                order.deliveryName,
                order.deliveryStreet,
                order.deliveryCity,
                order.deliveryState,
                order.deliveryZip,
                order.ccNumber,
                order.ccExpiration,
                order.ccCVV,
                order.placedAt,
            )
        )

        // 3. jdbcOperations.update: DB에 insert, GeneratedKeyHolder를 통해 자동 생성된 order id 조회
        val keyHolder = GeneratedKeyHolder()
        jdbcOperations.update(psc, keyHolder)
        val orderId: Long = keyHolder.keys?.get("id")?.toString()?.toLong()
            ?: throw RuntimeException("Failed to retrieve generated ID")
        order.id = orderId

        // 4. 해당 order Id를 바탕으로 각 Taco를 저장(tacoSave 호출)
        val tacos = order.tacos
        var i = 0
        for (taco in tacos) {
            saveTaco(orderId, i++, taco)
        }
        return order
    }

    private fun saveTaco(
        orderId: Long,
        orderKey: Int,
        taco: Taco,
    ) : Long {
        taco.createdAt = Date()
        val pscf = PreparedStatementCreatorFactory(
            "insert into Taco "
                    +"(name, created_at, taco_order, taco_order_key) "
                    +"values (?,?,?,?)",
            Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT
        )
        pscf.setReturnGeneratedKeys(true)

        val psc = pscf.newPreparedStatementCreator(
            listOf(
                taco.name,
                taco.createdAt,
                orderId,
                orderKey,
            )
        )
        // Taco를 저장하고 생성된 ID를 조회한 후, 해당 타코에 포함된 재료들을 Ingredient_Ref 테이블에 저장
        val keyHolder = GeneratedKeyHolder()
        jdbcOperations.update(psc, keyHolder)
        val tacoId = keyHolder.keys?.get("id")?.toString()?.toLong()
            ?: throw RuntimeException("Failed to retrieve generated ID")
        taco.id = tacoId
        // 재료의 ID를 저장
        saveIngredientRefs(tacoId, taco.ingredients)
        return tacoId
    }

    private fun saveIngredientRefs(
        tacoId: Long,
        ingredientRefs: List<IngredientRef>,
    ) {
        // Ingredient_Ref 테이블에 재료 ID, 타코 ID, 그리고 재료 순서(taco_key)를 저장
        // PreparedStatementCreator 없이 간단한 update 메서드로 처리
        var key = 0
        for (ingredientRef in ingredientRefs) {
            jdbcOperations.update(
                "insert into Ingredient_Ref (ingredient, taco, taco_key) "
                        + "values (?, ?, ?)",
                ingredientRef.ingredient, tacoId, key++
            )
        }
    }
}