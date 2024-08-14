package com.example.sakila;

import com.example.sakila.film.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static com.example.sakila.CommonStepDefs.caughtException;
import static org.mockito.Mockito.*;

public class FilmServiceStepDefs {

    FilmRepository mockRepository = mock(FilmRepository.class);
    FilmInput filmInput = new FilmInput();
    FilmInput invalidFilmInput = new FilmInput();
    Short currentFilmId;
    FilmResponse actualOutput;
    PartialFilmResponse actualOutputPartial;
    List<FilmResponse> actualOutputList;
    List<Film> filmList;
    Map<String, Object> validFilmUpdates = Map.of("title", "The Matrix");


    @Given("the database contains films")
    public void theDatabaseContainsFilms() {
        Film film1 = new Film();
        Film film2 = new Film();
        film1.setTitle("The Matrix");
        film2.setTitle("The Matrix Reloaded");

        filmList = List.of(film1, film2);
        doReturn(filmList).when(mockRepository).findAll();
    }

    @Given("the database contains a film with ID {short}")
    public void theDatabaseContainsAFilmWithID(short filmId) {
        Film film = new Film();
        film.setTitle("The Matrix");

        doReturn(java.util.Optional.of(film)).when(mockRepository).findById(filmId);
    }

    @Given("the database does not contain a film with ID {short}")
    public void theDatabaseDoesNotContainAFilmWithID(short filmId) {
        doReturn(java.util.Optional.empty()).when(mockRepository).findById(filmId);
    }

    @Given("the service receives a valid FilmInput")
    public void theServiceReceivesAValidFilmInput() {
        filmInput.setTitle("The Matrix");
        doReturn(new Film()).when(mockRepository).save(any());
    }

    @Given("the service receives an invalid FilmInput")
    public void theServiceReceivesAnInvalidFilmInput() {
        filmInput = invalidFilmInput;
        doThrow(new RuntimeException()).when(mockRepository).save(new Film());
    }

    @When("the service retrieves all films")
    public void theServiceRetrievesAllFilms() {
        FilmService service = new FilmService(mockRepository, null, null);
        actualOutputList = service.readAllFilms();
    }

    @When("the service retrieves a film with ID {short}")
    public void theServiceRetrievesAFilmWithID(short filmId) {
        try {
            FilmService service = new FilmService(mockRepository, null, null);
            actualOutput = service.readFilmById(filmId);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("the service creates a film")
    public void theServiceCreatesAFilm() {
        try {
            FilmService service = new FilmService(mockRepository, null, null);
            actualOutputPartial = service.createFilm(filmInput);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("the service updates a film with ID {short}")
    public void theServiceUpdatesAFilmWithID(short filmId) {
        try {
            FilmService service = new FilmService(mockRepository, null, null);
            actualOutputPartial = service.updateFilm(filmId, filmInput);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("the service patches a film with ID {short}")
    public void theServicePatchesAFilmWithID(short filmId) {
        try {
            FilmService service = new FilmService(mockRepository, null, null);
            actualOutputPartial = service.patchFilm(filmId, validFilmUpdates);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("the service deletes a film with ID {short}")
    public void theServiceDeletesAFilmWithID(short filmId) {
        currentFilmId = filmId;
        try {
            FilmService service = new FilmService(mockRepository, null, null);
            service.deleteFilm(filmId);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @And("the service returns a list of FilmResponse")
    public void theServiceReturnsAListOfFilmResponse() {
        assertNotNull(actualOutputList);
        assertInstanceOf(List.class, actualOutputList);
        actualOutputList.forEach(film -> assertInstanceOf(FilmResponse.class, film));
    }

    @And("the service returns a FilmResponse")
    public void theServiceReturnsAFilmResponse() {
        assertNotNull(actualOutput);
        assertInstanceOf(FilmResponse.class, actualOutput);
    }

    @And("the service returns an PartialFilmResponse")
    public void theServiceReturnsAnPartialFilmResponse() {
        assertNotNull(actualOutputPartial);
        assertInstanceOf(PartialFilmResponse.class, actualOutputPartial);
    }

    @And("the service deletes the film")
    public void theServiceDeletesTheFilm() {
        verify(mockRepository, times(1)).deleteById(currentFilmId);
    }
}
