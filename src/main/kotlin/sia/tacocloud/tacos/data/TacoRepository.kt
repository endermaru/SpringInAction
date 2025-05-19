package sia.tacocloud.tacos.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sia.tacocloud.tacos.Taco

@Repository
interface TacoRepository: JpaRepository<Taco, Long>