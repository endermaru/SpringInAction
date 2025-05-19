package sia.tacocloud.tacos.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.SecurityFilterChain
import sia.tacocloud.tacos.data.UserRepository
import java.util.*
import java.util.function.Supplier


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig
{
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(userRepo: UserRepository): UserDetailsService {
        return UserDetailsService { username ->
            userRepo.findByUsername(username)
                ?: throw UsernameNotFoundException("User '$username' not found")
        }
    }

    @Bean
    fun filterChain(
        http: HttpSecurity,
        customOAuth2UserService: CustomOAuth2UserService,
    ): SecurityFilterChain {
        http
            // antMatchers() 대신 requestMatchers()
            .authorizeHttpRequests { auth ->
                auth
//                    .requestMatchers("/design", "/orders").hasRole("USER")
//                    .requestMatchers(HttpMethod.POST, "/api/ingredients").hasAuthority("SCOPE_writeIngredients")
//                    .requestMatchers(HttpMethod.DELETE, "/api/ingredients").hasRole("SCOPE_deleteIngredients")
//                    .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                    .requestMatchers("/**","/","/oauth2/**", "/h2-console/**").permitAll()
            }
//            .oauth2ResourceServer { oauth2 ->
//                oauth2.jwt { }
//            }
            // 🔹 H2 Console iframe 사용 가능하도록 설정
            .headers { headers ->
                headers.frameOptions { it.sameOrigin() }
            }
            .formLogin { login ->
                login
                    // 커스텀 로그인 페이지 설정 가능
                    .loginPage("/login")
                    // 로그인 성공 시 이동할 페이지
                    .defaultSuccessUrl("/design", true)
            }
            .csrf { csrf ->
//                csrf.disable()
                csrf.ignoringRequestMatchers("/design", "/h2-console/**", "/api/orders")
            }
//            .oauth2Login { login ->
//                login
//                    .loginPage("/login")
//                    .userInfoEndpoint { userInfo ->
//                        userInfo.userService(customOAuth2UserService)
//                    }
//                    .defaultSuccessUrl("/design", false)
//            }
            .logout { logout ->
                //logout.logoutUrl("/logout")
                logout.logoutSuccessUrl("/")
            }

        return http.build()
    }
}