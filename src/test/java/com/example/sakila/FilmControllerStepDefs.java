package com.example.sakila;

import com.example.sakila.actor.PartialActorResponse;
import com.example.sakila.film.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FilmControllerStepDefs {

    FilmService mockService;
    FilmController controller;
    PartialFilmResponseController partialFilmResponseController;

    FilmResponse actualOutput;
    PartialFilmResponse actualOutputPartial;
    FilmInput filmInput;
    List<FilmResponse> actualOutputList;
    Exception caughtException;
    short currentFilmid;

    FilmInput invalidFilmInput;
    Map<String, Object> validFilmUpdates;

    @Before
    public void setup() {
        mockService = mock(FilmService.class);
        controller = new FilmController(mockService);
        partialFilmResponseController = new PartialFilmResponseController(mockService);
        filmInput = new FilmInput();
        invalidFilmInput = new FilmInput();
        validFilmUpdates = Map.of("title", "New Title");
    }

    @Given("a film exists with ID {short}")
    public void aFilmExistsWithID(short filmId) {
        Film film = new Film();
        film.setTitle("Film Title");
        currentFilmid = filmId;
        doReturn(new FilmResponse(film)).when(mockService).readFilmById(currentFilmid);

    }

    @Given("a film does not exist with ID {short}")
    public void aFilmDoesNotExistWithID(short filmId) {
        doReturn( new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).readFilmById(filmId);
        doReturn( new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).updateFilm(eq(filmId), any());
        doReturn( new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).deleteFilm(filmId);
        doReturn( new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).patchFilm(eq(filmId), any());
    }

    @Given("a valid FilmInput request body")
    public void aValidFilmInputRequestBody() {
        filmInput.setTitle("Film Title");
        doReturn( new PartialFilmResponse(new Film())).when(mockService).createFilm(filmInput);
        doReturn( new PartialFilmResponse(new Film())).when(mockService).updateFilm(currentFilmid, filmInput);
        doReturn( new PartialFilmResponse(new Film())).when(mockService).patchFilm(currentFilmid, validFilmUpdates);
    }

    @Given("an invalid FilmInput request body")
    public void anInvalidFilmInputRequestBody() {
        filmInput = invalidFilmInput;
        doThrow( new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(mockService).createFilm(filmInput);
        doThrow( new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(mockService).updateFilm(currentFilmid, filmInput);
        doThrow( new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(mockService).patchFilm(eq(currentFilmid), any());
    }

    @When("a GET request is made for a film with ID {short}")
    public void aGETRequestIsMadeForAFilmWithID(short filmId) {
        try {
            actualOutput = partialFilmResponseController.readFilmById(filmId);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("a POST request is made to the films collection")
    public void aPOSTRequestIsMadeToTheFilmsCollection() {
        try {
            actualOutputPartial = controller.createFilm(filmInput);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("a PUT request is made for a film with ID {short}")
    public void aPUTRequestIsMadeForAFilmWithID(short filmId) {
        try {
            actualOutputPartial = controller.updateFilm(filmId, filmInput);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("a PATCH request is made for a film with ID {short}")
    public void aPATCHRequestIsMadeForAFilmWithID(short filmId) {
        try {
            actualOutputPartial = controller.patchFilm(filmId, validFilmUpdates);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("a DELETE request is made for a film with ID {short}")
    public void aDELETERequestIsMadeForAFilmWithID(short filmId) {
        try {
            controller.deleteFilm(filmId);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @Then("a FilmResponse is returned")
    public void aFilmResponseIsReturned() {
        assertNotNull(actualOutput);
        assertInstanceOf(FilmResponse.class, actualOutput);
    }

    @Then("a PartialFilmResponse is returned")
    public void aPartialFilmResponseIsReturned() {
        assertNotNull(actualOutputPartial);
        assertInstanceOf(PartialFilmResponse.class, actualOutputPartial);
    }


}
