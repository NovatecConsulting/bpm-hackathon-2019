package info.novatec.igelcheckout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    private static final String CHECKOUT_TOPIC = "orderPlaced";

    @Autowired
    private KafkaTemplate<String, String> template;

    public void checkout(String id) {
        template.send(CHECKOUT_TOPIC, id, id);
    }

}
