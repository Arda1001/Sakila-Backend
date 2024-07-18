package com.example.sakila.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorResponseController {

    @Autowired
    private ActorService actorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ActorResponse> readAllActors() {
        return actorService.readAllActors();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ActorResponse readActorById(@PathVariable Short id) {
        return actorService.readActorById(id);
    }
}
