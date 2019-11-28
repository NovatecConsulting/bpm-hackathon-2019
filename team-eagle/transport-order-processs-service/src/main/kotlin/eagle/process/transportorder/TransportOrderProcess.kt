package eagle.process.transportorder

import io.zeebe.client.ZeebeClient
import io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder
import io.zeebe.gateway.protocol.GatewayGrpc
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.io.FileNotFoundException
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Controller
class TransportOrderProcess {

    private lateinit var zeebeClient: ZeebeClient

    val broker = "f22e7563-2c8f-4a38-9e38-428525018cf9.zeebe.camunda.io:443"
    val audience = "f22e7563-2c8f-4a38-9e38-428525018cf9.zeebe.camunda.io"
    val clientSecret = "7iVeetaNK44aNCq6IN1diuKIYFV6cH8LICChkTJ6QyiOssCF1gBSmIZb0YNGIwy4"
    val clientId = "bqukYPVXbFFmS4G1LhSkwGNTKLVZPnJ9"

    @PostConstruct
    fun connectAndDeploy() {
        val cred = OAuthCredentialsProviderBuilder()
                .audience(audience)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build()

        zeebeClient = ZeebeClient.newClientBuilder()
                .credentialsProvider(cred)
                .brokerContactPoint(broker)
                .build()

        LOGGER.info(javaClass.getResource("/").toURI().path)

//        val deploymentEvent = zeebeClient
//                .newDeployCommand()
//                .addResourceFromClasspath("bpmn/transport-order-process.bpmn")
//                .send().join()
//
//        LOGGER.info("Transport Order Process deployed with key: " + deploymentEvent.key)
    }

    @PreDestroy
    fun close() {
        zeebeClient.close()
    }

    @PostMapping("/transport-order")
    fun startProcessInstance(): ResponseEntity<String> {

        val workflowInstanceEvent = zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId("transport-order-process")
                .latestVersion()
                .variables(mapOf(Pair("processDisplayName", "Transport Order Process")))
                .send()
                .join()

        return ResponseEntity.ok().body(workflowInstanceEvent.workflowInstanceKey.toString())
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(TransportOrderProcess::class.java)
    }

}