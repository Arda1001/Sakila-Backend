package com.example.sakila.film;

import com.example.sakila.ValidationGroup;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;


@Getter
@Setter
public class FilmInput {
    @NotNull(groups = {ValidationGroup.Create.class})
    @Size(min = 1, max = 128)
    private String title;
    private String description;
    private int releaseYear;
    @NotNull(groups = {ValidationGroup.Create.class})
    @Min(0) @Max(255)
    private Short languageId;
    private Short originalLanguageId;
    @NotNull(groups = {ValidationGroup.Create.class})
    @Min(0) @Max(255)
    private int rentalDuration;
    @NotNull(groups = {ValidationGroup.Create.class})
    @DecimalMin("0.0") @DecimalMax("99.99")
    private double rentalRate;
    private int length;
    @NotNull(groups = {ValidationGroup.Create.class})
    @DecimalMin("0.0") @DecimalMax("999.999")
    private double replacementCost;
    private Rating rating;
    private Set<SpecialFeatures> specialFeatures;
    private Set<Short> castIds;
}

