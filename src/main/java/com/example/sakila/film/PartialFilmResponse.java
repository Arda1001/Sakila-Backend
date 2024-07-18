package com.example.sakila.film;

import lombok.Getter;

@Getter
public class PartialFilmResponse {
    private final Short id;
    private final String title;
    private final int releaseYear;

    public PartialFilmResponse(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.releaseYear = film.getReleaseYear();
    }
}
