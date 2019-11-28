package main.java.de.novatec;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.spring.client.ZeebeClientLifecycle;

@RestController
@RequestMapping(path = "/zeebe", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ServiceARestController {

    private static final String BPMN_PROCESS_ID = "demoProcess";

    @Autowired
    private ZeebeClientLifecycle client;

    @PostMapping(path = "/start")
    public ResponseEntity<String> startZeebe() {
        if (!client.isRunning()) {
            return ResponseEntity.notFound().build();
        }

        final WorkflowInstanceEvent event = client
            .newCreateInstanceCommand()
            .bpmnProcessId(BPMN_PROCESS_ID)
            .latestVersion()
            .variables("{\"a\": \"" + UUID.randomUUID().toString() + "\"}")
            .send()
            .join();

        return ResponseEntity.ok().build();
    }

}
