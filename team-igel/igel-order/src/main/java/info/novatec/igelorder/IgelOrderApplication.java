package info.novatec.igelorder;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;
import io.zeebe.client.api.response.DeploymentEvent;

@SpringBootApplication
public class IgelOrderApplication {

    @Autowired
    private ZeebeClientBuilder zeebeClientBuilder;

    public static void main(String[] args) {
        SpringApplication.run(IgelOrderApplication.class, args);
    }

    @PostConstruct
    public void deployZeebeProcess() {
        try (ZeebeClient client = zeebeClientBuilder.build()) {

            final DeploymentEvent deploymentEvent = client.newDeployCommand()
                .addResourceFromClasspath("order.bpmn")
                .send()
                .join();

            System.out.println("Deployment created with key: " + deploymentEvent.getKey());
        }
    }

}
