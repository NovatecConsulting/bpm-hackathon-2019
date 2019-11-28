package info.novatec.igelorder.kafka;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import info.novatec.messages.OrderPlacedEvent;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;

@Component
public class OrderPlacedListener {

    @Autowired
    private ZeebeClientBuilder zeebeClientBuilder;

    @KafkaListener(groupId = "orderPlaced", id = "orderPlacedListener", topics = "orderPlaced",
        autoStartup = "${listen.auto.start:true}",
        properties = {"auto.offset.reset=earliest"})
    public void listen(OrderPlacedEvent data) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("order", data);
        try (ZeebeClient client = zeebeClientBuilder.build()) {
            client.newPublishMessageCommand()
                .messageName("OrderPlacedMessage")
                .correlationKey(null)
                .variables(variables)
                .send()
                .join();
        }
    }

}
