package com.example.sakila.film;

import com.example.sakila.actor.PartialActorResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class FilmResponse {
    private final Short id;
    private final String title;
    private final int releaseYear;
    private final List<PartialActorResponse> cast;

    public FilmResponse(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.releaseYear = film.getReleaseYear();
        this.cast = film.getCast().stream().map(PartialActorResponse::new).toList();

    }
}
