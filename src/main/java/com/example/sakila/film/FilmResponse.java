package com.example.sakila.film;

import com.example.sakila.actor.PartialActorResponse;
import com.example.sakila.language.Language;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class FilmResponse {
    private final Short id;
    private final String title;
    private final int releaseYear;
    private final String description;
    private final int rentalDuration;
    private final double rentalRate;
    private final int length;
    private final double replacementCost;
    private final Rating rating;
    private final Set<SpecialFeatures> specialFeatures;
    private final Language language;
    private final Language originalLanguage;
    private final List<PartialActorResponse> cast;

    public FilmResponse(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.releaseYear = film.getReleaseYear();
        this.description = film.getDescription();
        this.rentalDuration = film.getRentalDuration();
        this.rentalRate = film.getRentalRate();
        this.length = film.getLength();
        this.replacementCost = film.getReplacementCost();
        this.rating = film.getRating();
        this.specialFeatures = film.getSpecialFeatures();
        this.language = film.getLanguageId();
        this.originalLanguage = film.getOriginalLanguageId();
        this.cast = film.getCast().stream().map(PartialActorResponse::new).toList();
    }
}
