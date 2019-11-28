package main.java.de.novatec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.annotation.ZeebeDeployment;

@SpringBootApplication
@EnableZeebeClient
@ZeebeDeployment(classPathResource = "process.bpmn")
public class MyServiceApplication {

    public static void main(String... args) {
        SpringApplication.run(MyServiceApplication.class, args);
    }

}