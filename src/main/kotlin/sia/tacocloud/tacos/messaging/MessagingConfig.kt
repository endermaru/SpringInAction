package sia.tacocloud.tacos.messaging

import jakarta.jms.Destination
import org.apache.activemq.artemis.jms.client.ActiveMQQueue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import sia.tacocloud.tacos.TacoOrder


@Configuration
class MessagingConfig {
    @Bean
    fun orderQueue(): Destination? {
        return ActiveMQQueue("tacocloud.order.queue")
    }

    @Bean
    fun messageConverter(): MappingJackson2MessageConverter {
        val messageConverter = MappingJackson2MessageConverter()
        messageConverter.setTypeIdPropertyName("_typeId")

        val typeIdMappings: MutableMap<String, Class<*>> = HashMap()
        typeIdMappings["order"] = TacoOrder::class.java
        messageConverter.setTypeIdMappings(typeIdMappings)

        return messageConverter
    }
}