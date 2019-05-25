package lv.announce.worker

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.amqp.rabbit.core.RabbitAdmin


@Configuration
class RabbitConfig(val properties: WorkerProperties) {

    @Bean
    fun rabbitAdmin(connectionFactory: ConnectionFactory) =
            RabbitAdmin(connectionFactory)

    @Bean
    fun queue(): Queue {
        val arguments = mapOf(
                "x-dead-letter-exchange" to DEFAULT_EXCHANGE_NAME,
                "x-message-ttl" to properties.notificationDelayMillis,
                "x-dead-letter-routing-key" to properties.workerNotificationsDestinationQueueName
        )
        return Queue(properties.workerNotificationsDelayStoreQueueName, true, false, false, arguments)
    }

    companion object {
        const val DEFAULT_EXCHANGE_NAME = ""
    }
}