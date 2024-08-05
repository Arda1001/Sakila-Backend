package com.example.sakila;

import com.example.sakila.actor.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ActorControllerStepDefs {

    private ActorService mockService;
    ActorController controller;
    private ActorResponseController responseController;

    ActorResponse actualOutput;
    PartialActorResponse createdActor;
    List<ActorResponse> actualOutputList;
    private Exception caughtException;

    @Before
    public void setup() {
        mockService = mock(ActorService.class);
        controller = new ActorController(mockService);
        responseController = new ActorResponseController(mockService);
    }

    @Given("an actor exists with ID {short}")
    public void anActorExistsWithID(short actorId) {
        Actor actor = new Actor();
        actor.setFirstName("JOHN");
        actor.setLastName("DOE");
        doReturn(new ActorResponse(actor)).when(mockService).readActorById(actorId);
    }

    @Given("an actor does not exist with ID {short}")
    public void anActorDoesNotExistWithID(short actorId) {
        doThrow( new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).readActorById(actorId);
    }

    @Given("a valid ActorInput request body")
    public void aValidActorInputRequestBody() {
        ActorInput actorInput = new ActorInput();
        actorInput.setFirstName("JOHN");
        actorInput.setLastName("DOE");
        doReturn(new Actor()).when(mockService).createActor(actorInput);
    }


    @When("a GET request is made for an actor with ID {short}")
    public void aGETRequestIsMadeToActors(short actorId) {
        try {
            actualOutput = responseController.readActorById(actorId);
        }
        catch (Exception ex) {
            caughtException = ex;
        }
    }

    @When("a GET request is made to the actors collection")
    public void aGETRequestIsMadeToTheActorsCollection() {
        try {
            actualOutputList = responseController.readAllActors();
        }
        catch (Exception ex) {
            caughtException = ex;
        }
    }

    @When("a POST request is made to the actors collection")
    public void aPOSTRequestIsMadeToTheActorsCollection() {
        try {
            createdActor = controller.create(new ActorInput());
        }
        catch (Exception ex) {
            caughtException = ex;
        }
    }

    @Then("a ActorResponse is returned")
    public void aActorResponseIsReturned() {
        assertNotNull(actualOutput);
        assertInstanceOf(ActorResponse.class, actualOutput);
    }

    @Then("a ResponseStatusException is thrown")
    public void aResponseStatusExceptionIsThrown() {
        assertNotNull(caughtException);
        assertInstanceOf(ResponseStatusException.class, caughtException);
    }

    @And("the status code is {int}")
    public void theStatusCodeIs(int statusCode) {
        assertEquals(statusCode, ((ResponseStatusException) caughtException).getStatusCode().value());
    }




}
