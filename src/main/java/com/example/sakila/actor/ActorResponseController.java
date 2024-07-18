package com.example.sakila.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorResponseController {

    @Autowired
    private ActorRepository actorRepository;

    @GetMapping
    public List<ActorResponse> readAllActors() {
        return actorRepository.findAll()
                .stream()
                .map(ActorResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ActorResponse readActorById(@PathVariable Short id) {
        return actorRepository.findById(id)
                .map(ActorResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
