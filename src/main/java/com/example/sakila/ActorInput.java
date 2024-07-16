package com.example.sakila;

import lombok.Data;

@Data
public class ActorInput {
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

