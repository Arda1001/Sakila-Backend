package com.example.sakila.film;

import com.example.sakila.language.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Stream;


@Getter
@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private short id;

    @Setter
    @Column(name = "title")
    private String title;

    @Setter
    @Column(name = "description")
    private String description;

    @Setter
    @Column(name = "release_year")
    private int releaseYear;

    @Setter
    @Column(name = "rental_duration")
    private int rentalDuration;

    @Setter
    @Column(name = "rental_rate")
    private double rentalRate;

    @Setter
    @Column(name = "length")
    private int length;

    @Setter
    @Column(name = "replacement_cost")
    private double replacementCost;

    @Setter
    @Enumerated(EnumType.STRING)
    @Convert(converter = RatingConverter.class)
    @Column(name = "rating")
    private Rating rating;

    @Setter
    @Column(name = "special_feature")
    private Set<String> specialFeatures;

    @Setter
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language languageId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "original_language_id")
    private Language originalLanguageId;

}

