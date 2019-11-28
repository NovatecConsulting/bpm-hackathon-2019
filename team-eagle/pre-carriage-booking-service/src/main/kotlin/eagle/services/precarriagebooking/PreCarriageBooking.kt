package eagle.services.precarriagebooking.eagle.services.precarriagebooking

import io.zeebe.client.ZeebeClient
import io.zeebe.client.api.worker.JobWorker
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class PreCarriageBooking {
    private lateinit var zeebeClient: ZeebeClient
    private lateinit var jobWorker: JobWorker
    private val jobType = "book-pre-carriage"

    @PostConstruct
    fun connectAndSubscribe() {
        zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint("127.0.0.1:26500")
                .usePlaintext()
                .build()

        jobWorker = zeebeClient.newWorker()
                .jobType(jobType)
                .handler { jobClient, job ->
                    LOGGER.info("Job " + jobType + " received")
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
        private val LOGGER = LoggerFactory.getLogger(PreCarriageBooking.javaClass)
    }
}