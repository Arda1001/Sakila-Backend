package com.example.sakila.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/films")
public class PartialFilmResponseController {

    private final FilmService filmService;

    @Autowired
    public PartialFilmResponseController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FilmResponse> readAllFilms() {
       return filmService.readAllFilms();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FilmResponse readFilmById(@PathVariable Short id) {
        return filmService.readFilmById(id);
    }
}
