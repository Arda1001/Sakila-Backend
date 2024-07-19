package com.example.sakila.actor;

import com.example.sakila.ValidationGroup;
import com.example.sakila.film.Film;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;


@Data
public class ActorInput {
    @NotNull(groups = {ValidationGroup.Create.class})
    @Size(min = 1, max = 128)
    private Short id;

    @NotNull(groups = {ValidationGroup.Create.class})
    @Size(min = 1, max = 45)
    private String firstName;

    @NotNull(groups = {ValidationGroup.Create.class})
    @Size(min = 1, max = 45)
    private String lastName;

    private Set<Film> films;

}

