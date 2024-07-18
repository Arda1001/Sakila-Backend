package com.example.sakila.film;

import com.example.sakila.language.Language;
import com.example.sakila.language.LanguageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;


    @PostMapping
    public Film createFilm(@RequestBody FilmInput filmInput) {
        Film film = new Film();
        BeanUtils.copyProperties(filmInput, film, "languageId", "originalLanguageId");

        if (filmInput.getLanguageId() != null) {
            Language language = languageRepository.findById(filmInput.getLanguageId())
                    .orElseThrow(() -> new RuntimeException("Language not found with id: " + filmInput.getLanguageId()));
            film.setLanguageId(language);
        }

        if (filmInput.getOriginalLanguageId() != null) {
            Language originalLanguage = languageRepository.findById(filmInput.getOriginalLanguageId())
                    .orElseThrow(() -> new RuntimeException("Original language not found with id: " + filmInput.getOriginalLanguageId()));
            film.setOriginalLanguageId(originalLanguage);
        }

        film.setSpecialFeatures(filmInput.getSpecialFeatures());

        return filmRepository.save(film);
    }

    @PutMapping("/{id}")
    public Film updateFilm(@PathVariable Short id, @RequestBody FilmInput updatedFilmInput) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));

        BeanUtils.copyProperties(updatedFilmInput, film, "id", "languageId", "originalLanguageId");

        if (updatedFilmInput.getLanguageId() != null) {
            Language language = languageRepository.findById(updatedFilmInput.getLanguageId())
                    .orElseThrow(() -> new RuntimeException("Language not found with id: " + updatedFilmInput.getLanguageId()));
            film.setLanguageId(language);
        }

        if (updatedFilmInput.getOriginalLanguageId() != null) {
            Language originalLanguage = languageRepository.findById(updatedFilmInput.getOriginalLanguageId())
                    .orElseThrow(() -> new RuntimeException("Original language not found with id: " + updatedFilmInput.getOriginalLanguageId()));
            film.setOriginalLanguageId(originalLanguage);
        }
        else {
            film.setOriginalLanguageId(null);
        }

        film.setSpecialFeatures(updatedFilmInput.getSpecialFeatures());

        return filmRepository.save(film);
    }

    @PatchMapping("/{id}")
    public Film patchFilm(@PathVariable Short id, @RequestBody Map<String, Object> updates) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));

        BeanWrapper beanWrapper = new BeanWrapperImpl(film);
        updates.forEach((key, value) -> {
            switch (key) {
                case "languageId":
                    Optional<Language> language = languageRepository.findById(((Number) value).shortValue());
                    language.ifPresent(film::setLanguageId);
                    break;
                case "originalLanguageId":
                    Optional<Language> originalLanguage = languageRepository.findById(((Number) value).shortValue());
                    originalLanguage.ifPresent(film::setOriginalLanguageId);
                    break;
                case "specialFeatures":
                    Set<SpecialFeatures> specialFeatures = ((List<String>) value).stream()
                            .map(SpecialFeatures::valueOf)
                            .collect(Collectors.toSet());
                    film.setSpecialFeatures(specialFeatures);
                    break;
                default:
                    if (beanWrapper.isWritableProperty(key)) {
                        beanWrapper.setPropertyValue(key, value);
                    }
                    else {
                        throw new IllegalArgumentException("Unknown attribute: " + key);
                    }
                    break;
            }
        });

        return filmRepository.save(film);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Short id) {
        filmRepository.deleteById(id);
    }
}
