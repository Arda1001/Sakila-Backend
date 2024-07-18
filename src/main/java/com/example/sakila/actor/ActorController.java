package com.example.sakila.actor;

import com.example.sakila.ValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

    public List<Actor> readAll() {
        return actorRepository.findAll();
    }

    @PostMapping
    public Actor create(@Validated(ValidationGroup.Create.class) @RequestBody ActorInput data) {
        final var actor = new Actor();
        actor.setFirstName(data.getFirstName());
        actor.setLastName(data.getLastName());
        return actorRepository.save(actor);
    }

    @PutMapping("/{id}")
    public Actor update(@Validated(ValidationGroup.Update.class)@PathVariable Short id, @RequestBody ActorInput data) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor not found with id: " + id));
        actor.setFirstName(data.getFirstName());
        actor.setLastName(data.getLastName());
        return actorRepository.save(actor);
    }

    @PatchMapping("/{id}")
    public Actor patchActor(@Validated(ValidationGroup.Update.class)@PathVariable Short id, @RequestBody Map<String, Object> updates) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    actor.setFirstName((String) value);
                    break;
                case "lastName":
                    actor.setLastName((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown attribute: " + key);
            }
        });

        return actorRepository.save(actor);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Short id) {
        actorRepository.deleteById(id);
    }

}
