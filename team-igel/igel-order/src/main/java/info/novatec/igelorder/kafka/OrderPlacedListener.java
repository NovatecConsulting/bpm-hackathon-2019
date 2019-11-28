package info.novatec.igelorder.kafka;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import info.novatec.messages.OrderPlacedEvent;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;
import io.zeebe.client.api.response.WorkflowInstanceEvent;

@Component
public class OrderPlacedListener {

    @Autowired
    private ZeebeClientBuilder zeebeClientBuilder;

    @KafkaListener(groupId = "orderPlaced", id = "orderPlacedListener", topics = "orderPlaced",
        autoStartup = "${listen.auto.start:true}")
    public void listen(OrderPlacedEvent data) {
        System.out.println("Message receied: " + data.toString());

        Map<String, Object> variables = new HashMap<>();
        variables.put("order", data);
        try (ZeebeClient client = zeebeClientBuilder.build()) {
            WorkflowInstanceEvent workflowInstanceEvent = client.newCreateInstanceCommand()
                .bpmnProcessId("OrderProcessId")
                .latestVersion()
                .variables(variables)
                .send()
                .join();

            System.out
                .println("Workflow-Instance with key " + workflowInstanceEvent.getWorkflowInstanceKey() + " created.");
        }
    }

}
