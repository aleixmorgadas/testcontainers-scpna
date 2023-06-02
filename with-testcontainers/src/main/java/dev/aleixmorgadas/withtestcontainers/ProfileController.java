package dev.aleixmorgadas.withtestcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ProfileController.URI)
public class ProfileController {
    public static final String URI = "/profile";

    @Autowired
    ProfileRepository repository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping()
    ResponseEntity<List<Profile>> all() {
        kafkaTemplate.send("request", "all");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/insert")
    ResponseEntity<Profile> save() {
        kafkaTemplate.send("request", "save");
        var profile = new Profile(UUID.randomUUID().toString(), "Luis");
        repository.save(profile);
        return ResponseEntity.ok(profile);
    }
}
