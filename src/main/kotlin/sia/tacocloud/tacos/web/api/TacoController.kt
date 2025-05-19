package sia.tacocloud.tacos.web.api

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sia.tacocloud.tacos.Taco
import sia.tacocloud.tacos.TacoOrder
import sia.tacocloud.tacos.data.OrderRepository
import sia.tacocloud.tacos.data.TacoRepository


@RestController
@RequestMapping(path= ["/api/tacos"], produces = ["application/json"])
// CORS 설정 : localhost:8080에서 오는 브라우저 요청만 허용
@CrossOrigin(origins = ["http://localhost:8080"])
class TacoController(
    private val tacoRepo: TacoRepository,
    private val orderRepo: OrderRepository,
) {
    // Get의 url param 지정 -> /api/tacos?recent
    @GetMapping(params=["recent"])
    fun recentTacos(): Iterable<Taco> {
        val page = PageRequest.of(0,12, Sort.by("createdAt").descending())
        // Page<Taco>에서 실제 데이터 리스트를 반환
        return tacoRepo.findAll(page).content
    }

    @GetMapping("/{id}")
    fun tacoById(
        @PathVariable("id") id: Long,
    ) : ResponseEntity<Taco> {
        val taco: Taco? = tacoRepo.findByIdOrNull(id)
        return if (taco != null) {
            ResponseEntity(taco, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun postTaco(
        @RequestBody taco: Taco,
    ): Taco {
        return tacoRepo.save(taco)
    }

    @PatchMapping("/{orderId}", consumes = ["application/json"])
    fun patchOrder(
        @PathVariable("orderId") orderId: Long,
        @RequestBody patch: TacoOrder,
    ): TacoOrder {
        val order = orderRepo.findByIdOrNull(orderId)
            ?: return orderRepo.save(patch)
        if (patch.deliveryName != null) {
            order.deliveryName = patch.deliveryName
        }
        if (patch.deliveryStreet != null) {
            order.deliveryStreet = patch.deliveryStreet
        }
        if (patch.deliveryCity != null) {
            order.deliveryCity = patch.deliveryCity
        }
        if (patch.deliveryState != null) {
            order.deliveryState = patch.deliveryState
        }
        if (patch.deliveryZip != null) {
            order.deliveryZip = patch.deliveryZip
        }
        if (patch.ccNumber != null) {
            order.ccNumber = patch.ccNumber
        }
        if (patch.ccExpiration != null) {
            order.ccExpiration = patch.ccExpiration
        }
        if (patch.ccCVV != null) {
            order.ccCVV = patch.ccCVV
        }
        return orderRepo.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delteORder(
        @PathVariable("orderId") orderId: Long,
    ) {
        try {
            orderRepo.deleteById(orderId)
        } catch (e: EmptyResultDataAccessException) {
        }
    }

}