package com.example.sakila.film;

import com.example.sakila.ValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@CrossOrigin
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmResponse createFilm(@Validated(ValidationGroup.Create.class) @RequestBody FilmInput data) {
        final var film = filmService.createFilm(data);
        return new FilmResponse(film);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FilmResponse updateFilm(@Validated(ValidationGroup.Update.class)@PathVariable Short id, @RequestBody FilmInput updatedFilmInput) {
        final var film = filmService.updateFilm(id, updatedFilmInput);
        return new FilmResponse(film);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FilmResponse patchFilm(@Validated(ValidationGroup.Update.class)@PathVariable Short id, @RequestBody Map<String, Object> updates) {
        final var film = filmService.patchFilm(id, updates);
        return new FilmResponse(film);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilm(@PathVariable Short id) {
        filmService.deleteFilm(id);
    }
}
