package com.example.sakila.film;

import com.example.sakila.language.Language;
import com.example.sakila.language.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @GetMapping
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Short id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
    }

    @PostMapping
    public Film createFilm(@RequestBody FilmInput filmInput) {
        Film film = new Film();
        film.setTitle(filmInput.getTitle());
        film.setDescription(filmInput.getDescription());
        film.setReleaseYear(filmInput.getReleaseYear());
        film.setRentalDuration(filmInput.getRentalDuration());
        film.setRentalRate(filmInput.getRentalRate());
        film.setLength(filmInput.getLength());
        film.setReplacementCost(filmInput.getReplacementCost());
        film.setRating(filmInput.getRating());
        film.setSpecialFeatures(filmInput.getSpecialFeatures());

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

        return filmRepository.save(film);
    }

    @PutMapping("/{id}")
    public Film updateFilm(@PathVariable Short id, @RequestBody FilmInput updatedFilmInput) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));

        film.setTitle(updatedFilmInput.getTitle());
        film.setDescription(updatedFilmInput.getDescription());
        film.setReleaseYear(updatedFilmInput.getReleaseYear());
        film.setRentalDuration(updatedFilmInput.getRentalDuration());
        film.setRentalRate(updatedFilmInput.getRentalRate());
        film.setLength(updatedFilmInput.getLength());
        film.setReplacementCost(updatedFilmInput.getReplacementCost());
        film.setRating(updatedFilmInput.getRating());
        film.setSpecialFeatures(updatedFilmInput.getSpecialFeatures());

        if (updatedFilmInput.getLanguageId() != null) {
            Language language = languageRepository.findById(updatedFilmInput.getLanguageId())
                    .orElseThrow(() -> new RuntimeException("Language not found with id: " + updatedFilmInput.getLanguageId()));
            film.setLanguageId(language);
        }

        if (updatedFilmInput.getOriginalLanguageId() != null) {
            Language originalLanguage = languageRepository.findById(updatedFilmInput.getOriginalLanguageId())
                    .orElseThrow(() -> new RuntimeException("Original language not found with id: " + updatedFilmInput.getOriginalLanguageId()));
            film.setOriginalLanguageId(originalLanguage);
        } else {
            film.setOriginalLanguageId(null);
        }

        return filmRepository.save(film);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Short id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
        filmRepository.delete(film);
    }
}


