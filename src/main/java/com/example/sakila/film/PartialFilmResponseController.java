package com.example.sakila.film;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class PartialFilmResponseController {

    @Autowired
    private FilmService filmService;

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
