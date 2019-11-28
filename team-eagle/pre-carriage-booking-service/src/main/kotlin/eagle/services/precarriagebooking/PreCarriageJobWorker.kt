package eagle.services.precarriagebooking

import io.zeebe.client.ZeebeClient
import io.zeebe.client.api.response.ActivatedJob
import io.zeebe.client.api.worker.JobClient
import io.zeebe.client.api.worker.JobHandler
import io.zeebe.client.api.worker.JobWorker
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class PreCarriageJobWorker {
    private lateinit var zeebeClient: ZeebeClient
    private lateinit var jobWorkerBookingRequests: JobWorker
    private lateinit var jobWorkerCancellationRequests: JobWorker

    @PostConstruct
    fun connectAndSubscribe() {
        zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint("127.0.0.1:26500")
                .usePlaintext()
                .build()

        jobWorkerBookingRequests = zeebeClient.newWorker()
                .jobType("book-pre-carriage")
                .handler(AirfreightBookingHandler())
                .open()

        jobWorkerCancellationRequests = zeebeClient.newWorker()
                .jobType("cancel-pre-carriage")
                .handler(AirfreightCancellationHandler())
                .open()
    }

    @PreDestroy
    fun close() {
        jobWorkerBookingRequests.close()
        jobWorkerCancellationRequests.close()
        zeebeClient.close()
    }

    class AirfreightBookingHandler : JobHandler {
        override fun handle(client: JobClient, job: ActivatedJob) {
            LOGGER.info("Booking request received")
            if (shouldFail()) {
                LOGGER.info("Job failed: " + job.key)
                client.newFailCommand(job.key)
                        .retries(job.retries - 1)
                        .errorMessage("Simulated Failure on Pre-Carriage Booking")
                        .send().join()
            } else {
                client.newCompleteCommand(job.key)
                        .variables(mapOf(Pair("booking", if (shouldReject()) "rejected" else "confirmed")))
                        .send().join()
            }
        }

        fun shouldFail(): Boolean {
            return if (Math.random() < 0.75) false else true
        }

        fun shouldReject(): Boolean {
            return if (Math.random() < 0.75) false else true
        }

    }

    class AirfreightCancellationHandler : JobHandler {
        override fun handle(client: JobClient, job: ActivatedJob) {
            LOGGER.info("Cancellation request received")
            client.newCompleteCommand(job.key)
                    .variables(mapOf(Pair("booking", "cancelled")))
                    .send().join()
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(PreCarriageJobWorker.javaClass)
    }
}