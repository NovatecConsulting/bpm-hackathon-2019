package eagle.process.transportorder

import io.zeebe.client.ZeebeClient
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Controller
class TransportOrderProcess {

    private lateinit var zeebeClient: ZeebeClient

    @PostConstruct
    fun connectAndDeploy() {
        zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint("127.0.0.1:26500")
                .usePlaintext()
                .build()

        val deploymentEvent = zeebeClient
                .newDeployCommand()
                .addResourceFromClasspath("bpmn/transport-order-process.bpmn")
                .send().join()

        LOGGER.info("Transport Order Process deployed with key: " + deploymentEvent.key)
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
                .send()
                .join()

        return ResponseEntity.noContent().build()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(TransportOrderProcess.javaClass)
    }

}