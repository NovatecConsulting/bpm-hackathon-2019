package main.java.de.novatec;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.spring.client.annotation.ZeebeWorker;

@Component
public class ServiceZeebeWorker {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceZeebeWorker.class);

    @ZeebeWorker(type = "serviceA")
    public void serviceA(final JobClient client, final ActivatedJob job) {
        logJob(job);
        client.newCompleteCommand(job.getKey()).variables("{\"foo\": \"bar\"}").send().join();
    }

    @ZeebeWorker(type = "serviceB")
    public void serviceB(final JobClient client, final ActivatedJob job) {
        logJob(job);
        client.newCompleteCommand(job.getKey()).variables("{\"bar\": \"foo\"}").send().join();
    }

    @ZeebeWorker(type = "serviceC")
    public void serviceC(final JobClient client, final ActivatedJob job) {
        logJob(job);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("fooBar",
            job.getVariablesAsMap().get("foo") + " " + job.getVariablesAsMap().get("bar"));

        client.newCompleteCommand(job.getKey()).variables(variables).send().join();
    }

    private static void logJob(final ActivatedJob job) {
        LOGGER.info(
            "complete job\n>>> [type: {}, key: {}, element: {}, workflow instance: {}]\n{deadline; {}]\n[headers: {}]\n[variables: {}]",
            job.getType(),
            job.getKey(),
            job.getElementId(),
            job.getWorkflowInstanceKey(),
            Instant.ofEpochMilli(job.getDeadline()),
            job.getCustomHeaders(),
            job.getVariables());
    }
}
