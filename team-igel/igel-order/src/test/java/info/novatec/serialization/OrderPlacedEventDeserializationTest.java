package info.novatec.serialization;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import info.novatec.messages.OrderPlacedEvent;

public class OrderPlacedEventDeserializationTest {

    OrderPlacedEvent ope = new OrderPlacedEvent("123", "Topf", 14);

    JsonDeserializer<OrderPlacedEvent> deserializer = new JsonDeserializer<OrderPlacedEvent>();
    JsonSerializer<OrderPlacedEvent> serializer = new JsonSerializer<OrderPlacedEvent>();

    @BeforeEach
    void setUp() {
        deserializer.configure(consumerConfigs(), false);
    }

    @Test
    void ope_should_be_deserializable() {
        Headers headers = new RecordHeaders();
        byte[] byteArray = serializer.serialize("myTopic", headers, ope);
        OrderPlacedEvent reconstructedOpe = deserializer.deserialize("myTopic", headers, byteArray);

        Assertions.assertEquals(ope.equals(reconstructedOpe), true);
    }

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "info.novatec.messages");
        return props;
    }
}
