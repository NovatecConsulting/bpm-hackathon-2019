package de.novatec;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
public class Producer {
    private Source source;

    public Producer(Source source) {
        super();
        this.source = source;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
