package com.example.sakila.film;

import com.example.sakila.ValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@Validated(ValidationGroup.Create.class) @RequestBody FilmInput data) {
        return filmService.createFilm(data);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Film updateFilm(@Validated(ValidationGroup.Update.class)@PathVariable Short id, @RequestBody FilmInput updatedFilmInput) {
        return filmService.updateFilm(id, updatedFilmInput);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Film patchFilm(@Validated(ValidationGroup.Update.class)@PathVariable Short id, @RequestBody Map<String, Object> updates) {
        return filmService.patchFilm(id, updates);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilm(@PathVariable Short id) {
        filmService.deleteFilm(id);
    }
}
