package tacos

import org.hamcrest.Matchers.containsString
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(HomeController::class)
class HomeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testHomePage() {
        // mockMvc가 HomeController에 "/"로 GET 요청을 보냄
        mockMvc.perform(get("/"))
            .andExpect(status().isOk)  // HTTP 200 Response를 예상
            .andExpect(view().name("home")) // "home" view를 예상
            // view 내용(문자열) 예상
            .andExpect(content().string(containsString("Welcome to...")))
    }
}