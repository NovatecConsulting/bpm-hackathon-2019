package de.novatec.schadenbuchen.flow;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;
import io.zeebe.spring.client.annotation.ZeebeWorker;
import org.springframework.beans.factory.annotation.Autowired;

public class TransferMoney implements JobHandler {

    @Autowired
    BankService bankService;

    //TODO type ergaenzen
    @ZeebeWorker(type="geld-ueberweisen")
    public void handle(JobClient client, ActivatedJob job) throws Exception {

        String customerId = (String) job.getVariablesAsMap().get("customerId");
        int summe = (int) job.getVariablesAsMap().get("auszahlung");
        BankDetails bankDetails = bankService.getBankDetails(customerId);

        System.out.println("Es wurden " + summe + "€ an die IBAN" + bankDetails.getIban() + " überwiesen");

        client.newCompleteCommand(job.getKey()).send().join();
    }
}
