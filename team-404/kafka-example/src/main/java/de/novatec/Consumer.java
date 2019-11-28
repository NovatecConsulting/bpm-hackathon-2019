package de.novatec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(Sink.class)
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private Producer producer;

    @StreamListener(target = Sink.INPUT)
    public void consume(String message) {
        logger.info("received a string message: " + message);

        producer.getSource().output().send(MessageBuilder.withPayload("").setHeader("type", "string").build());
    }


}
