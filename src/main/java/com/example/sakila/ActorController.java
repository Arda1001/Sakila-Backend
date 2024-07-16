package com.example.sakila;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

    public List<Actor> readAll() {
        return actorRepository.findAll();
    }

    @GetMapping("/actors/{id}")
    public Actor findActor(@PathVariable Short id) {
        return actorRepository.findById(id).get();
    }

    @GetMapping("/actors")
    public List<Actor> listActorsByName() {
        return actorRepository.findAll();
    }


    @PostMapping("/actors")
    public Actor create(@RequestBody ActorInput data) {
        final var actor = new Actor();
        actor.setFirstName(data.getFirstName());
        actor.setLastName(data.getLastName());
        return actorRepository.save(actor);
    }

    @PutMapping("/actors/{id}")
    public Actor update(@PathVariable Short id, @RequestBody ActorInput data) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor not found with id: " + id));
        actor.setFirstName(data.getFirstName());
        actor.setLastName(data.getLastName());
        return actorRepository.save(actor);
    }

    @DeleteMapping("/actors/{id}")
    public void delete(@PathVariable Short id) {
        actorRepository.findById(id).ifPresent(actorRepository::delete);
    }

//    @GetMapping("/actors")
//    public List<Actor> listActorsByName(@RequestParam String name) {
//        return actorRepository.findAllByFirstNameWithQuery(name);
//    }

}
