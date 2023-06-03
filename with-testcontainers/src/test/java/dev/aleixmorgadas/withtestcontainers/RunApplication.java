package dev.aleixmorgadas.withtestcontainers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public class RunApplication {

    public static void main(String[] args) {
        SpringApplication.from(WithTestcontainersApplication::main)
                .with(Configuration.class)
                .run(args);
    }

    @TestConfiguration
    static class Configuration {
        @Bean
        @RestartScope
        @ServiceConnection
        KafkaContainer kafka() {
            return new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.0"))
                    .withReuse(true);
        }

        @Bean
        @RestartScope
        @ServiceConnection
        PostgreSQLContainer<?> postgres() {
            return new PostgreSQLContainer<>("postgres:15.1-alpine")
                    .withReuse(true);
        }
    }
}
