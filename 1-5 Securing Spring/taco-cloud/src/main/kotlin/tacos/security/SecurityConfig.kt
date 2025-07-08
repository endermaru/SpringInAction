package tacos.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import tacos.data.UserRepository

@Configuration
class SecurityConfig(
    private val userRepository: UserRepository,
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

//    // In-memory 방식
//    @Bean
//    fun userDetailsService(
//        encoder: PasswordEncoder,
//    ) : UserDetailsService {
//        val usersList = mutableListOf<UserDetails>()
//        usersList.add(
//            User(
//                "buzz",
//                encoder.encode("password"),
//                listOf(SimpleGrantedAuthority("ROLE_USER"))
//            )
//        )
//        usersList.add(
//            User(
//                "woody",
//                encoder.encode("password"),
//                listOf(SimpleGrantedAuthority("ROLE_USER"))
//            )
//        )
//        return InMemoryUserDetailsManager(usersList)
//    }

    // JPA 방식
    @Bean
    fun userDetailsService(userRepo: UserRepository): UserDetailsService {
        return UserDetailsService { username ->
            userRepo.findByUsername(username)
                ?: throw UsernameNotFoundException("User '$username' not found")
        }
    }
}