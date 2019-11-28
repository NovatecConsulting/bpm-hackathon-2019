package de.novatec;

import io.zeebe.spring.client.EnableZeebeClient;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
@EnableZeebeClient
public class SchadenBerechnungsService {

    public static void main(String... args) {
        SpringApplication.run(SchadenBerechnungsService.class, args);
    }

}