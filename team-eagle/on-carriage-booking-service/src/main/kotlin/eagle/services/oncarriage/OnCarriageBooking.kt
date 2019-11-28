package eagle.services.oncarriage

import io.zeebe.client.ZeebeClient
import io.zeebe.client.api.worker.JobWorker
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class OnCarriageBooking {

    private lateinit var zeebeClient: ZeebeClient

    private lateinit var jobWorker: JobWorker

    @PostConstruct
    fun connectAndSubscribe() {
        zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint("127.0.0.1:26500")
                .usePlaintext()
                .build()

        jobWorker = zeebeClient.newWorker()
                .jobType("book-on-carriage")
                .handler { jobClient, job ->
                    LOGGER.info("Job received")
                    jobClient.newCompleteCommand(job.key)
                            .send()
                            .join()
                }
                .open()
    }

    @PreDestroy
    fun close() {
        jobWorker.close()
        zeebeClient.close()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(OnCarriageBooking.javaClass)
    }

}