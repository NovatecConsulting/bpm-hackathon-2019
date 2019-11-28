package info.novatec.igelcheckout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import info.novatec.messages.OrderPlacedEvent;

@Service
public class CheckoutService {

    private static final String CHECKOUT_TOPIC = "orderPlaced";

    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> template;

    public void checkout(OrderPlacedEvent order) {
        template.send(CHECKOUT_TOPIC, order.getOrderId(), order);
    }

}
