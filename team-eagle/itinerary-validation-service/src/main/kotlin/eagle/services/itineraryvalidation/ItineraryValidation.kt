package eagle.services.itineraryvalidation

import io.zeebe.client.ZeebeClient
import io.zeebe.client.api.worker.JobWorker
import io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


@Component
class ItineraryValidation {

    private lateinit var zeebeClient: ZeebeClient
    private lateinit var jobWorker: JobWorker

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

        jobWorker = zeebeClient.newWorker()
                .jobType("validate-itinerary")
                .handler { jobClient, job ->
                    LOGGER.info("Job received")
                    jobClient.newCompleteCommand(job.key)
                            .variables(mapOf(Pair("itinerary", checkItinerary())))
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

    fun checkItinerary(): String {
        return if (Math.random() < 0.75) "confirmed" else "rejected"
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ItineraryValidation.javaClass)
    }

}