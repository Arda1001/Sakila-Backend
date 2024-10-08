package com.example.sakila.actor;

import com.example.sakila.ValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/actors")
public class ActorController {
    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PartialActorResponse create(@Validated(ValidationGroup.Create.class) @RequestBody ActorInput data) {
        return actorService.createActor(data);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PartialActorResponse update(@Validated(ValidationGroup.Update.class)@PathVariable Short id, @RequestBody ActorInput data) {
        return actorService.updateActor(data, id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PartialActorResponse patchActor(@Validated(ValidationGroup.Update.class)@PathVariable Short id, @RequestBody Map<String, Object> updates) {
        return actorService.patchActor(id, updates);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Short id) {
        actorService.deleteActor(id);
    }

}
