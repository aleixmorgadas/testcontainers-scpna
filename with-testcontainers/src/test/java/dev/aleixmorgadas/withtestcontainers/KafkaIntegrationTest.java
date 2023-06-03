package dev.aleixmorgadas.withtestcontainers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.waitAtMost;

@SpringBootTest
@Testcontainers
@TestPropertySource(properties = "spring.kafka.consumer.auto-offset-reset=earliest")
class KafkaIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TestListener testListener;

    @Test
    void consumeMessage() {
        this.kafkaTemplate.send("test", "test-data");

        waitAtMost(Duration.ofSeconds(30)).untilAsserted(() -> {
            assertThat(this.testListener.messages).hasSize(1);
        });
    }

    @TestConfiguration
    static class Config {

        @Bean
        TestListener testListener() {
            return new TestListener();
        }

    }

    static class TestListener {

        private final List<String> messages = new ArrayList<>();

        @KafkaListener(topics = "test", groupId = "test")
        void listen(String data) {
            this.messages.add(data);
        }

    }

}