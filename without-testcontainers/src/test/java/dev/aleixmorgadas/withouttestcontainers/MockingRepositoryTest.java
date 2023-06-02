package dev.aleixmorgadas.withouttestcontainers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MockingRepositoryTest {

    @MockBean
    ProfileRepository repository;

    @Test
    void usingMocks() {
        String id = "my-profile";
        when(repository.findById(id)).thenReturn(Optional.of(new Profile(id, "Aleix")));

        var profile = repository.findById(id);
        assertThat(profile).isPresent();
        assertThat(profile.get().getName()).isEqualTo("Aleix");
    }
}
