package dev.aleixmorgadas.withtestcontainers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class ConsumerService {

    @KafkaListener(topics = "request", groupId = "test-group")
    void listener(String message) {
        System.out.println("Received Message in group foo: " + message);
    }
}
