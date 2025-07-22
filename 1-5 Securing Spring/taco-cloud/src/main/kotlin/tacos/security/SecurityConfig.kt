package tacos.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import tacos.data.UserRepository

@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val userRepository: UserRepository,
    private val customOAuth2UserService: CustomOAuth2UserService,
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

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/design", "/orders").hasRole("USER")
                    // .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/", "/**").permitAll()
            }
            .formLogin { login ->
                login
                    .loginPage("/login") // 커스텀 로그인 페이지 설정 가능
                    .defaultSuccessUrl("/", true) // 로그인 성공 시 이동할 페이지(강제 리디렉트)
            }
            .oauth2Login { login ->
                login
                    .loginPage("/login")
                    .userInfoEndpoint { userInfo ->
                        userInfo.userService(customOAuth2UserService)
                    }
                    .defaultSuccessUrl("/design", false)
            }
            .logout { logout ->
                // Security가 가로챌 logout url - /logout이 기본
                // logout.logoutUrl("/logout")
                // 성공 시 이동할 url
                logout.logoutSuccessUrl("/")
            }
            // H2 Console iframe 사용 가능하도록 설정
            .headers { headers ->
                headers.frameOptions { it.sameOrigin() }
            }
            // h2-console 경로의 CSRF 비활성화
            .csrf { csrf ->
                csrf.ignoringRequestMatchers("/h2-console/**")
            }
            .build();
    }
}