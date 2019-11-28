package info.novatec.igelorder;

import info.novatec.messages.OrderPlacedEvent;
import java.util.HashMap;
import java.util.Map;

import info.novatec.messages.PaymentReceivedEvent;
import info.novatec.messages.RetrievePaymentCommand;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
//@EnableKafka
public class KafkaConfig {

    @Value("${kafka.bootstrapservers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

//    @Bean
//    public ConsumerFactory<String, PaymentReceivedEvent> paymentReceivedConsumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
//    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Object, Object>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Object, Object> orderPlacedEventFactory = new ConcurrentKafkaListenerContainerFactory<>();
        orderPlacedEventFactory.setConsumerFactory(consumerFactory());
        orderPlacedEventFactory.setConcurrency(3);
        orderPlacedEventFactory.getContainerProperties().setPollTimeout(3000);
        orderPlacedEventFactory.setAutoStartup(true);
        orderPlacedEventFactory.setMissingTopicsFatal(false);
        return orderPlacedEventFactory;
    }

//    @Bean
//    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, PaymentReceivedEvent>> paymentReceivedCommandKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, PaymentReceivedEvent> paymentReceivedFactory = new ConcurrentKafkaListenerContainerFactory<>();
//        paymentReceivedFactory.setConsumerFactory(paymentReceivedConsumerFactory());
//        paymentReceivedFactory.setConcurrency(3);
//        paymentReceivedFactory.getContainerProperties().setPollTimeout(3000);
//        paymentReceivedFactory.setAutoStartup(true);
//        paymentReceivedFactory.setMissingTopicsFatal(false);
//        return paymentReceivedFactory;
//    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "igelOrder");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "info.novatec.messages");
        return props;
    }

    @Bean
    public ProducerFactory<String, RetrievePaymentCommand> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // See https://kafka.apache.org/documentation/#producerconfigs for more properties
        props.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
        return props;
    }

    @Bean
    public KafkaTemplate<String, RetrievePaymentCommand> kafkaTemplate() {
        return new KafkaTemplate<String, RetrievePaymentCommand>(producerFactory());
    }
}
