package com.example.sakila.film;


import com.example.sakila.actor.ActorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/films")
public class PartialFilmResponseController {

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping
    public List<PartialFilmResponse> readAllFilms() {
        return filmRepository.findAll()
                .stream()
                .map(PartialFilmResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    public PartialFilmResponse readFilmById(@PathVariable Short id) {
        return filmRepository.findById(id)
                .map(PartialFilmResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
