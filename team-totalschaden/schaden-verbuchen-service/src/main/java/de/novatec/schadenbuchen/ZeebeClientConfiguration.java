package de.novatec.schadenbuchen;

import io.zeebe.client.ZeebeClient;
import org.springframework.context.annotation.Bean;

public class ZeebeClientConfiguration {

    @Bean
    public ZeebeClient zeebe() {
        ZeebeClient zeebeClient = ZeebeClient.newClient();
        return zeebeClient;
    }
}
