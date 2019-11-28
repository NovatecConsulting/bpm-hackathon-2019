package info.novatec.igelorder.kafka;

import info.novatec.messages.OrderPlacedEvent;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedListener {

    @Autowired
    private ZeebeClient zeebeClient;

    @KafkaListener(id = "orderPlacedListener", topics = "orderPlaced")
    public void listen(OrderPlacedEvent order) {
        System.out.println("Message received: " + order.toString());

        WorkflowInstanceEvent workflowInstanceEvent = zeebeClient.newCreateInstanceCommand()
            .bpmnProcessId("OrderProcessId")
            .latestVersion()
            .variables(order)
            .send()
            .join();

        System.out
            .println("Workflow-Instance with key " + workflowInstanceEvent.getWorkflowInstanceKey() + " created.");
    }

}
