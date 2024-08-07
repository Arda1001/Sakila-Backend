package com.example.sakila;

import com.example.sakila.film.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    List<FilmResponse> filmResponses;
    Exception caughtException;
    short currentFilmid;
    FilmInput invalidFilmInput;


    @Before
    public void setup() {
        mockService = mock(FilmService.class);
        controller = new FilmController(mockService);
        partialFilmResponseController = new PartialFilmResponseController(mockService);
        filmInput = new FilmInput();
        invalidFilmInput = new FilmInput();
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
    }

    @Given("an invalid FilmInput request body")
    public void anInvalidFilmInputRequestBody() {
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

    }

    @When("a PUT request is made for a film with ID {short}")
    public void aPUTRequestIsMadeForAFilmWithID(short filmId) {
    }

    @When("a PATCH request is made for a film with ID {short}")
    public void aPATCHRequestIsMadeForAFilmWithID(short filmId) {
    }

    @When("a DELETE request is made for a film with ID {short}")
    public void aDELETERequestIsMadeForAFilmWithID(short filmId) {
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
