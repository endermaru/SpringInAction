package tacos.security

import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import tacos.data.UserRepository
import tacos.domain.User

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
    @Lazy private val passwordEncoder: PasswordEncoder,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private val logger = LoggerFactory.getLogger(CustomOAuth2UserService::class.java)

    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {

        val oauth2User = DefaultOAuth2UserService().loadUser(userRequest)

        val attributes = oauth2User.attributes
        val email = attributes["email"] as String
        val name = attributes["name"] as String
        val googleId = attributes["sub"] as String // Google 고유 ID

        logger.info("OAuth2 로그인 요청: email=$email, name=$name, googleId=$googleId")

        // DB에 사용자 존재 여부 확인
        var user = userRepository.findByUsername(email)
        if (user == null) {
            // 회원가입 (새 사용자 저장)
            user = User(
                username = email,
                password = passwordEncoder.encode(email.reversed()),
                fullname = name,
            )
            userRepository.save(user)
        }

        // 사용자 정보에 username 추가
        val attributesAdd = mutableMapOf<String, Any>().apply {
            putAll(oauth2User.attributes) // 기존 attributes 추가
            put("username", email) // username 추가
        }

        // ROLE_USER를 가진 DefaultOauthUser 반환
        return DefaultOAuth2User(
            listOf(SimpleGrantedAuthority("ROLE_USER")),
            attributesAdd, // 사용자 정보
            "username" // 사용자 식별키(ID) - 구글 기본은 "email"
        )
    }
}