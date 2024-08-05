package com.example.sakila.actor;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public List<ActorResponse> readAllActors() {
        return actorRepository.findAll()
                .stream()
                .map(ActorResponse::new)
                .toList();
    }

    public ActorResponse readActorById(Short id) {
        return actorRepository.findById(id)
                .map(ActorResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public PartialActorResponse createActor(ActorInput data){
        final var actor = new Actor(data.getFirstName(), data.getLastName(), data.getFilms());
        actor.setFirstName(data.getFirstName());
        actor.setLastName(data.getLastName());
        return new PartialActorResponse(actorRepository.save(actor));
    }

    public PartialActorResponse updateActor(ActorInput data, Short id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor not found with id: " + id));
        actor.setFirstName(data.getFirstName());
        actor.setLastName(data.getLastName());
        return new PartialActorResponse(actorRepository.save(actor));
    }

    public PartialActorResponse patchActor(Short id, Map<String, Object> updates) {
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

    public void deleteActor(Short id) {
        actorRepository.deleteById(id);
    }
}



