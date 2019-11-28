package info.novatec.igelorder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;

@Configuration
public class ZeebeConfiguration {

    @Value("${zeebe.brokercontactpoint}")
    private String brokerContactPoint;

    @Bean
    public ZeebeClientBuilder zeebeClientBuilder() {
        return ZeebeClient.newClientBuilder().brokerContactPoint(brokerContactPoint).usePlaintext();
    }

    @Bean(destroyMethod = "close")
    public ZeebeClient zeebeClient(ZeebeClientBuilder builder) {
        return builder.build();
    }
}
