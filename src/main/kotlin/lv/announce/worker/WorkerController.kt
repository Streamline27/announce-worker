package lv.announce.worker

import lv.announce.worker.config.WorkerProperties
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class WorkerController(
        private val rabbitTemplate: RabbitTemplate,
        private val properties: WorkerProperties
) {
    private val log = LoggerFactory.getLogger(WorkerController::class.java)

    @GetMapping("/hello")
    fun hello() = "Hello from service:" + properties.applicationName

    @PostMapping("/notification")
    fun send(@RequestBody message: String): Result {
        log.info("Notification: Forwarding to push queue message:[$message]")
        rabbitTemplate.convertAndSend(properties.workerNotificationsDelayQueueName, message)
        return Result(
                message = message,
                serviceName = properties.applicationName
        )
    }

    data class Result(
            val message: String,
            val serviceName: String
    )
}