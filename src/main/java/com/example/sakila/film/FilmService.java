package com.example.sakila.film;


import com.example.sakila.actor.Actor;
import com.example.sakila.actor.ActorRepository;
import com.example.sakila.language.Language;
import com.example.sakila.language.LanguageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ActorRepository actorRepository;

    private FilmInput data;

    public List<FilmResponse> readAllFilms() {
        return filmRepository.findAll()
                .stream()
                .map(FilmResponse::new)
                .toList();
    }

    public FilmResponse readFilmById(Short id) {
        return filmRepository.findById(id)
                .map(FilmResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public PartialFilmResponse createFilm(FilmInput data) {
        Film film = new Film();
        BeanUtils.copyProperties(data, film, "languageId", "originalLanguageId");

        if (data.getLanguageId() != null) {
            Language language = languageRepository.findById(data.getLanguageId())
                    .orElseThrow(() -> new RuntimeException("Language not found with id: " + data.getLanguageId()));
            film.setLanguageId(language);
        }

        if (data.getOriginalLanguageId() != null) {
            Language originalLanguage = languageRepository.findById(data.getOriginalLanguageId())
                    .orElseThrow(() -> new RuntimeException("Original language not found with id: " + data.getOriginalLanguageId()));
            film.setOriginalLanguageId(originalLanguage);
        }

        film.setSpecialFeatures(data.getSpecialFeatures());

        if (data.getCastIds() != null && !data.getCastIds().isEmpty()) {
            Set<Actor> cast = data.getCastIds().stream()
                    .map(id -> actorRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Actor not found with id: " + id)))
                    .collect(Collectors.toSet());
            film.setCast(cast);
        }

        return new PartialFilmResponse(filmRepository.save(film));
    }

    public PartialFilmResponse updateFilm(Short id, FilmInput updatedFilmInput) {
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

        return new PartialFilmResponse(filmRepository.save(film));
    }

    public PartialFilmResponse patchFilm(Short id, Map<String, Object> updates) {
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

        return new PartialFilmResponse(filmRepository.save(film));
    }

    public void deleteFilm(Short id) {
        filmRepository.deleteById(id);
    }






}
