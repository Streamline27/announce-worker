package lv.announce.worker.config

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
                "x-message-ttl" to NOTIFICATION_DELAY_MILLIS,
                "x-dead-letter-routing-key" to properties.workerNotificationsDestinationQueueName
        )
        return Queue(properties.workerNotificationsDelayQueueName, true, false, false, arguments)
    }

    companion object {
        const val DEFAULT_EXCHANGE_NAME = ""
        const val NOTIFICATION_DELAY_MILLIS = 15000
    }
}