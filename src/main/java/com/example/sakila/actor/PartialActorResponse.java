package com.example.sakila.actor;


import lombok.Getter;

@Getter
public class PartialActorResponse {
    private final Short id;
    private final String firstName;
    private final String lastName;

    public PartialActorResponse(Actor actor) {
        this.id = actor.getId();
        this.firstName = actor.getFirstName();
        this.lastName = actor.getLastName();
    }
}
