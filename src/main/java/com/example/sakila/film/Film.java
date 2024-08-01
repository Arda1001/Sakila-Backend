package com.example.sakila.film;

import com.example.sakila.actor.Actor;
import com.example.sakila.language.Language;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


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
    @Convert(converter = RatingConverter.class)
    @Column(name = "rating")
    private Rating rating;

    @Setter
    @Convert(converter = SpecialFeaturesConverter.class)
    @Column(name = "special_features")
    private Set<SpecialFeatures> specialFeatures;

    @Setter
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language languageId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "original_language_id")
    private Language originalLanguageId;

    @Setter
    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> cast = new HashSet<>();

}

