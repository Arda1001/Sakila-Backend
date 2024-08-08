package com.example.sakila.film;

import lombok.Getter;

@Getter
public enum SpecialFeatures {
    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");

private final String displayName;

    SpecialFeatures(String displayName) {
        this.displayName = displayName;
    }

}

