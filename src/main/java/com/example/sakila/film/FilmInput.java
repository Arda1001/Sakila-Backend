package com.example.sakila.film;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmInput {
    private String title;
    private String description;
    private int releaseYear;
    private Short languageId;
    private Short originalLanguageId;
    private int rentalDuration;
    private double rentalRate;
    private int length;
    private double replacementCost;
    private String rating;
    private String specialFeatures;
}

