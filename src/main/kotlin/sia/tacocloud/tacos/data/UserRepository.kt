package sia.tacocloud.tacos.data

import org.springframework.data.repository.CrudRepository
import sia.tacocloud.tacos.User

interface UserRepository : CrudRepository<User, Long> {
    fun findByUsername(name: String): User?
}