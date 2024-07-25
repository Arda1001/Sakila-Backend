package com.example.sakila.actor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorResponseController {

    @Autowired
    private ActorService actorService;

    public ActorResponseController(ActorService actorService) {
        this.actorService = actorService;
    }

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
