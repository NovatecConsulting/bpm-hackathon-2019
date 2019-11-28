package info.novatec.igelorder.worker;

import java.time.Duration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;
import io.zeebe.client.api.worker.JobWorker;

@Component
public class DummyWorker {

    @Autowired
    private ZeebeClientBuilder builder;

    private ZeebeClient client;

    private JobWorker worker;

    @PostConstruct
    public void initWorker() {
        client = builder.build();

        System.out.println("Opening job worker.");

        worker = client
            .newWorker()
            .jobType("dummyTopic")
            .handler(new DummyWorkerImpl())
            .timeout(Duration.ofSeconds(10))
            .open();

        System.out.println("Job worker opened and receiving jobs.");
        System.out.println("Job worker is open?: " + worker.isOpen());
    }

    @PreDestroy
    public void close() {
        System.out.println("Closing down Dummy Worker!");
        worker.close();
        client.close();
    }

    private static class DummyWorkerImpl implements JobHandler {

        @Override
        public void handle(JobClient client, ActivatedJob job) {
            System.out.println("Dummy worker is working, handling, partying.");
            client.newCompleteCommand(job.getKey()).send().join();
        }
    }
}
