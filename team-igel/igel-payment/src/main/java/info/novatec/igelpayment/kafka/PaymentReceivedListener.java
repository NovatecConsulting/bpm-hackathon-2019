package info.novatec.igelpayment.kafka;

import info.novatec.igelpayment.service.PaymentService;
import info.novatec.messages.RetrievePaymentCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentReceivedListener {

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(id = "paymentReceivedListener", topics = "retrievePayment")
    public void receivePaymentRequest(RetrievePaymentCommand retrievePaymentCommand){
        paymentService.retrievePayment(retrievePaymentCommand);
    }
}
