package lv.announce.worker.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class WorkerProperties(
        @Value("\${spring.application.name}") val applicationName: String,
        @Value("\${worker.rabbit.queue.notifications-destination}") val workerNotificationsDestinationQueueName: String,
        @Value("\${worker.rabbit.queue.notifications-destination}") val workerNotificationsDelayQueueName: String
)