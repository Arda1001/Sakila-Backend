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

    private static final String LANGUAGE_ID = "languageId";
    private static final String ORIGINAL_LANGUAGE_ID = "originalLanguageId";
    private static final String ID = "id";
    private static final String LANGUAGE_NOT_FOUND = "Language not found with id: ";
    private static final String ORIGINAL_LANGUAGE_NOT_FOUND = "Original language not found with id: ";
    private static final String ACTOR_NOT_FOUND = "Actor not found with id: ";
    private static final String FILM_NOT_FOUND = "Film not found with id: ";
    private static final String SPECIAL_FEATURES = "specialFeatures";

    private final FilmRepository filmRepository;
    private final LanguageRepository languageRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository, LanguageRepository languageRepository, ActorRepository actorRepository) {
        this.filmRepository = filmRepository;
        this.languageRepository = languageRepository;
        this.actorRepository = actorRepository;
    }

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
        BeanUtils.copyProperties(data, film, LANGUAGE_ID, ORIGINAL_LANGUAGE_ID);

        if (data.getLanguageId() != null) {
            Language language = languageRepository.findById(data.getLanguageId())
                    .orElseThrow(() -> new RuntimeException(LANGUAGE_NOT_FOUND + data.getLanguageId()));
            film.setLanguageId(language);
        }

        if (data.getOriginalLanguageId() != null) {
            Language originalLanguage = languageRepository.findById(data.getOriginalLanguageId())
                    .orElseThrow(() -> new RuntimeException(ORIGINAL_LANGUAGE_NOT_FOUND + data.getOriginalLanguageId()));
            film.setOriginalLanguageId(originalLanguage);
        }

        film.setSpecialFeatures(data.getSpecialFeatures());

        if (data.getCastIds() != null && !data.getCastIds().isEmpty()) {
            Set<Actor> cast = data.getCastIds().stream()
                    .map(id -> actorRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException(ACTOR_NOT_FOUND + id)))
                    .collect(Collectors.toSet());
            film.setCast(cast);
        }

        return new PartialFilmResponse(filmRepository.save(film));
    }

    public PartialFilmResponse updateFilm(Short id, FilmInput updatedFilmInput) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(FILM_NOT_FOUND + id));

        BeanUtils.copyProperties(updatedFilmInput, film, ID, LANGUAGE_ID, ORIGINAL_LANGUAGE_ID);

        if (updatedFilmInput.getLanguageId() != null) {
            Language language = languageRepository.findById(updatedFilmInput.getLanguageId())
                    .orElseThrow(() -> new RuntimeException(LANGUAGE_NOT_FOUND + updatedFilmInput.getLanguageId()));
            film.setLanguageId(language);
        }

        if (updatedFilmInput.getOriginalLanguageId() != null) {
            Language originalLanguage = languageRepository.findById(updatedFilmInput.getOriginalLanguageId())
                    .orElseThrow(() -> new RuntimeException(ORIGINAL_LANGUAGE_NOT_FOUND + updatedFilmInput.getOriginalLanguageId()));
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
                .orElseThrow(() -> new RuntimeException(FILM_NOT_FOUND + id));

        BeanWrapper beanWrapper = new BeanWrapperImpl(film);
        updates.forEach((key, value) -> {
            switch (key) {
                case LANGUAGE_ID:
                    Optional<Language> language = languageRepository.findById(((Number) value).shortValue());
                    language.ifPresent(film::setLanguageId);
                    break;
                case ORIGINAL_LANGUAGE_ID:
                    Optional<Language> originalLanguage = languageRepository.findById(((Number) value).shortValue());
                    originalLanguage.ifPresent(film::setOriginalLanguageId);
                    break;
                case SPECIAL_FEATURES:
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
