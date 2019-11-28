package eagle.services.oncarriage

import io.zeebe.client.ZeebeClient
import io.zeebe.client.api.response.ActivatedJob
import io.zeebe.client.api.worker.JobClient
import io.zeebe.client.api.worker.JobHandler
import io.zeebe.client.api.worker.JobWorker
import io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class OnCarriageJobWorker {
    private lateinit var zeebeClient: ZeebeClient
    private lateinit var jobWorkerBookingRequests: JobWorker
    private lateinit var jobWorkerCancellationRequests: JobWorker

    val audience = "f22e7563-2c8f-4a38-9e38-428525018cf9.zeebe.camunda.io"
    val broker = "f22e7563-2c8f-4a38-9e38-428525018cf9.zeebe.camunda.io:443"
    val clientSecret = "7iVeetaNK44aNCq6IN1diuKIYFV6cH8LICChkTJ6QyiOssCF1gBSmIZb0YNGIwy4"
    val clientId = "bqukYPVXbFFmS4G1LhSkwGNTKLVZPnJ9"

    @PostConstruct
    fun connectAndSubscribe() {
        val cred = OAuthCredentialsProviderBuilder()
                .audience(audience)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build()

        zeebeClient = ZeebeClient.newClientBuilder()
                .credentialsProvider(cred)
                .brokerContactPoint(broker)
                .build()

        jobWorkerBookingRequests = zeebeClient.newWorker()
                .jobType("book-on-carriage")
                .handler(AirfreightBookingHandler())
                .open()

        jobWorkerCancellationRequests = zeebeClient.newWorker()
                .jobType("cancel-on-carriage")
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
                        .errorMessage("Simulated Failure on On-Carriage Booking")
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
        private val LOGGER = LoggerFactory.getLogger(OnCarriageJobWorker.javaClass)
    }
}