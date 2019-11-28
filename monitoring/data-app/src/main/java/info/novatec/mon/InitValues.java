package info.novatec.mon;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

@Component
public class InitValues implements JavaDelegate {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InitValues.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        VariableMap variableMap = Variables.putValue("vollstaendig", Math.random() > 0.3)
                .putValue("vorqualifizierung_erfolgreich", Math.random() > 0.2)
                .putValue("meinung_ok", Math.random() > 0.1)
                .putValue("zusage", Math.random() > 0.1);

        log.info("initialized with {}", variableMap);

        execution.setVariables(variableMap);
    }
}
