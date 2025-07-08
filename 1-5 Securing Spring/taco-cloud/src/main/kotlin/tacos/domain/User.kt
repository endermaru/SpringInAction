package tacos.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long = 0,

    private val username: String = "",
    private val password: String = "",
    private val fullname: String = "",
    private val street: String = "",
    private val city: String = "",
    private val state: String = "",
    private val zip: String = "",
    private val phoneNumber: String = "",

    ): UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String = password
    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    companion object {
        private const val serialVersionUID: Long = 1L
    }
}