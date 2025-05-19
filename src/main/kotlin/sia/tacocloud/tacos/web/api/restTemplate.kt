package sia.tacocloud.tacos.web.api

import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import sia.tacocloud.tacos.Ingredient

@Bean
fun restTemplate(): RestTemplate {
    return RestTemplate()
}

