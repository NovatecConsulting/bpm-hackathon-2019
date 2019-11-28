package de.novatec.kfzschadenregulierung;

import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.annotation.ZeebeDeployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableZeebeClient
@ZeebeDeployment(classPathResource = "schaden-regulierung.bpmn")
public class KfzSchadenRegulierungApplication {

	public static void main(String[] args) {
		SpringApplication.run(KfzSchadenRegulierungApplication.class, args);
	}

}
