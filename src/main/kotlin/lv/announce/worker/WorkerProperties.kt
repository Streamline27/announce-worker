package lv.announce.worker

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class WorkerProperties(
        @Value("\${spring.application.name}") val applicationName: String,
        @Value("\${worker.notification-delay-millis}") val notificationDelayMillis: Long,
        @Value("\${worker.rabbit.queue.notifications-destination}") val workerNotificationsDestinationQueueName: String,
        @Value("\${worker.rabbit.queue.notifications-delay-store}") val workerNotificationsDelayStoreQueueName: String
)