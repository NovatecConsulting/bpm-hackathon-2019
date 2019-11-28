package info.novatec.igelorder.worker;

import info.novatec.messages.OrderPlacedEvent;
import info.novatec.messages.PaymentReceivedEvent;
import info.novatec.messages.RetrievePaymentCommand;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;
import io.zeebe.client.api.worker.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;

@Component
public class PaymentJobWorker implements JobHandler {

    @Autowired
    private ZeebeClientBuilder builder;

    private ZeebeClient client;

    private JobWorker worker;

    @Autowired
    private KafkaTemplate<String, RetrievePaymentCommand> template;

    @PostConstruct
    public void initWorker() {
        client = builder.build();

        System.out.println("Opening job worker.");

        worker = client
            .newWorker()
            .jobType("retrievePayment")
            .handler(this)
            .timeout(Duration.ofSeconds(10))
            .open();

        System.out.println("Job worker opened and receiving jobs.");
        System.out.println("Job worker is open?: " + worker.isOpen());
    }

    @PreDestroy
    public void close() {
        System.out.println("Closing down Payment Worker!");
        worker.close();
    }

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        OrderPlacedEvent order = job.getVariablesAsType(OrderPlacedEvent.class);
        System.out.println("Payment worker is working, handling, partying: " + order);
        template.send("retrievePayment", order.getOrderId(), new RetrievePaymentCommand(order.getOrderId(), order.getItem(), order.getAmount()));
        client.newCompleteCommand(job.getKey())
                .send().join();
    }

    @KafkaListener(id = "paymentReceivedListener", topics = "paymentReceived")
    public void listen(PaymentReceivedEvent payment) {
        System.out.println("Payment received: " + payment.toString());

        client.newPublishMessageCommand()
                .messageName("paymentReceived")
                .correlationKey(payment.getOrderId())
                .variables(payment)
                .send()
                .join();
    }
}
