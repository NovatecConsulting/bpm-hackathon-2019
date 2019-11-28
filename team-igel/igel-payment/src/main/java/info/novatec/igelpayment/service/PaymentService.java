package info.novatec.igelpayment.service;

import info.novatec.messages.PaymentReceivedEvent;
import info.novatec.messages.RetrievePaymentCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final String RETRIEVE_PAYMENT_TOPIC = "paymentReceived";

    @Autowired
    private KafkaTemplate<String, PaymentReceivedEvent> template;

    public void retrievePayment(RetrievePaymentCommand paymentCommand) {
        System.out.println("retrieving");
        template.send(RETRIEVE_PAYMENT_TOPIC, paymentCommand.getOrderId(), new PaymentReceivedEvent(paymentCommand.getOrderId(), paymentCommand.getItem(), paymentCommand.getAmount(), Math.random()*25));
    }

}
