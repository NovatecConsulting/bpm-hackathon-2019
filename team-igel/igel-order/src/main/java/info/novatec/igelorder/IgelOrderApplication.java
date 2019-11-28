package info.novatec.igelorder;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class IgelOrderApplication {

    @Autowired
    private ZeebeClient zeebeClient;

    public static void main(String[] args) {
        SpringApplication.run(IgelOrderApplication.class, args);
    }

    @PostConstruct
    public void deployZeebeProcess() {
        final DeploymentEvent deploymentEvent = zeebeClient.newDeployCommand()
            .addResourceFromClasspath("order.bpmn")
            .send()
            .join();

        System.out.println("Deployment created with key: " + deploymentEvent.getKey());
    }

}
