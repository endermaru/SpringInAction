package tacos.domain

import org.springframework.security.crypto.password.PasswordEncoder

data class RegistrationForm(
    private val username: String,
    private val password: String,
    private val fullname: String,
    private val street: String,
    private val city: String,
    private val state: String,
    private val zip: String,
    private val phone: String,
) {
    fun toUser(passwordEncoder: PasswordEncoder): User {
        return User(
            username = username,
            password = passwordEncoder.encode(password),
            fullname = fullname,
            street = street,
            city = city,
            state = state,
            zip = zip,
            phoneNumber = phone,
        )
    }
}