package dev.aleixmorgadas.withouttestcontainers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class JpaIntegrationTest {
    public static final String ID = "my-profile";

    @Autowired
    ProfileRepository repository;

    @BeforeEach
    void setup() {
        repository.save(new Profile(ID, "Aleix"));
    }

    @AfterEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void myProfileIsPresent() {
        var profile = repository.findById(ID);
        assertThat(profile).isPresent();
        assertThat(profile.get().getName()).isEqualTo("Aleix");
    }
}
