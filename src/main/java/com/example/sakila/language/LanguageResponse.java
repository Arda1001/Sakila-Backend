package com.example.sakila.language;

import lombok.Getter;

@Getter
public class LanguageResponse {
    private final Short id;
    private final String name;


    public LanguageResponse(Language language) {
        this.id = language.getId();
        this.name = language.getName();
    }
}
