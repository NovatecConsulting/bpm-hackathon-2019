package de.novatec.kfzschadenregulierung;

import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.spring.client.ZeebeClientLifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ReguliereService {

    @Autowired
    ZeebeClientLifecycle zeebeClientLifecycle;

    @RequestMapping(value = "/schaden", method = RequestMethod.POST)
    public ResponseEntity<String> reguliere(@RequestBody Schaden schaden) throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("customerId", schaden.getCustomerId());
        variables.put("schadenTyp", schaden.getSchadenTyp());
        WorkflowInstanceEvent kfzSchadenRegulieren = zeebeClientLifecycle.newCreateInstanceCommand().bpmnProcessId("KfzSchadenRegulieren").latestVersion().variables(variables).send().join();
        return ResponseEntity.ok(String.valueOf(kfzSchadenRegulieren.getWorkflowInstanceKey()));
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> status() {
        return ResponseEntity.ok(zeebeClientLifecycle.isRunning());
    }

}
