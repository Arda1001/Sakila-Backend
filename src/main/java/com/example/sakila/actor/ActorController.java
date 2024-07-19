package com.example.sakila.actor;

import com.example.sakila.ValidationGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private final ActorService actorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Actor create(@Validated(ValidationGroup.Create.class) @RequestBody ActorInput data) {
        return actorService.createActor(data);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Actor update(@Validated(ValidationGroup.Update.class)@PathVariable Short id, @RequestBody ActorInput data) {
        return actorService.updateActor(data, id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Actor patchActor(@Validated(ValidationGroup.Update.class)@PathVariable Short id, @RequestBody Map<String, Object> updates) {
        return actorService.patchActor(id, updates);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Short id) {
        actorService.deleteActor(id);
    }

}
