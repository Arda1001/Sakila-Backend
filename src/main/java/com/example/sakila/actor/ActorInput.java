package com.example.sakila.actor;

import com.example.sakila.ValidationGroup;
import com.example.sakila.film.Film;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;


@Data
public class ActorInput {

    @NotNull(groups = {ValidationGroup.Create.class})
    private String firstName;

    @NotNull(groups = {ValidationGroup.Create.class})
    private String lastName;

    private Set<Film> films;

}

