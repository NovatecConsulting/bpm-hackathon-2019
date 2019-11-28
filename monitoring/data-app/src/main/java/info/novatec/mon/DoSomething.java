package info.novatec.mon;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class DoSomething implements JavaDelegate {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DoSomething.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        int duration = 1000 + (int) (Math.random() * 2000);
        log.info("Task {} waits for {} milliseconds",  execution.getCurrentActivityName(), duration);
        Thread.sleep(duration);
    }
}
