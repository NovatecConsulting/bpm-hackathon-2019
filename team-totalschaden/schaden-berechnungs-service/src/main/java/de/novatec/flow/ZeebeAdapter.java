package de.novatec.flow;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobWorker;
import io.zeebe.spring.client.annotation.ZeebeWorker;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ZeebeAdapter {

    public static final String CUSTOMER_ID = "customerId";
    public static final String SCHADEN_TYP = "schadenTyp";
    public static final String AUSZAHLUNG = "auszahlung";

    private JobWorker subscription;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    HistoryService historyService;

    @ZeebeWorker(type = "schaden-berechnen-z")
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        String customerId = (String) job.getVariablesAsMap().get(CUSTOMER_ID);
        String schadenTyp = (String) job.getVariablesAsMap().get(SCHADEN_TYP);
        Map<String, Object> variables = new HashMap<>();
        variables.put(CUSTOMER_ID, customerId);
        variables.put(SCHADEN_TYP, schadenTyp);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ClaimsCalculatorService", String.valueOf(job.getKey()), variables);
        int auszahlung = (int) historyService.createHistoricVariableInstanceQuery().variableName(AUSZAHLUNG).processInstanceId(processInstance.getId()).singleResult().getValue();
        Map<String, Object> result = new HashMap<>();
        result.put(AUSZAHLUNG, auszahlung);
        client.newCompleteCommand(job.getKey()).variables(result).send().join();
    }
}
