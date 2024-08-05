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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ActorControllerStepDefs {

    private ActorService mockService;
    ActorController controller;
    private ActorResponseController responseController;

    ActorResponse actualOutput;
    PartialActorResponse actualOutputPartial;
    ActorInput actorInput;
    List<ActorResponse> actualOutputList;
    private Exception caughtException;
    short currentActorId;

    ActorInput invalidActorInput;
    Map<String, Object> validActorUpdates;

    @Before
    public void setup() {
        mockService = mock(ActorService.class);
        controller = new ActorController(mockService);
        responseController = new ActorResponseController(mockService);
        actorInput = new ActorInput();
        invalidActorInput = new ActorInput();
        validActorUpdates = Map.of("firstName", "JOHN", "lastName", "DOE");
    }

    @Given("an actor exists with ID {short}")
    public void anActorExistsWithID(short actorId) {
        Actor actor = new Actor();
        actor.setFirstName("JOHN");
        actor.setLastName("DOE");
        currentActorId = actorId;
        doReturn(new ActorResponse(actor)).when(mockService).readActorById(currentActorId);
    }

    @Given("an actor does not exist with ID {short}")
    public void anActorDoesNotExistWithID(short actorId) {
        doThrow( new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).readActorById(actorId);
        doThrow( new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).updateActor(any(), eq(actorId));
        doThrow( new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).deleteActor(actorId);
        doThrow (new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).patchActor(eq(actorId), any());
    }

    @Given("a valid ActorInput request body")
    public void aValidActorInputRequestBody() {
        actorInput.setFirstName("JOHN");
        actorInput.setLastName("DOE");
        doReturn(new PartialActorResponse(new Actor())).when(mockService).createActor(actorInput);
        doReturn(new PartialActorResponse(new Actor())).when(mockService).updateActor(actorInput, currentActorId);
        doReturn(new PartialActorResponse(new Actor())).when(mockService).patchActor(currentActorId, validActorUpdates);
    }

    @Given("an invalid ActorInput request body")
    public void anInvalidActorInputRequestBody() {
        actorInput = invalidActorInput;
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(mockService).createActor(actorInput);
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(mockService).updateActor(actorInput, currentActorId);
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(mockService).patchActor(eq(currentActorId), any());
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
            actualOutputPartial = controller.create(actorInput);
        }
        catch (Exception ex) {
            caughtException = ex;
        }
    }

    @When("a PUT request is made for an actor with ID {short}")
    public void aPUTRequestIsMadeForAnActorWithID(short actorId) {
        try {
            actualOutputPartial = controller.update(actorId, actorInput);
        }
        catch (Exception ex) {
            caughtException = ex;
        }
    }

    @When("a DELETE request is made for an actor with ID {short}")
    public void aDELETERequestIsMadeForAnActorWithID(short actorId) {
        try {
            controller.delete(actorId);
        }
        catch (Exception ex) {
            caughtException = ex;
        }
    }

    @When("a PATCH request is made for an actor with ID {short}")
    public void aPATCHRequestIsMadeForAnActorWithID(short actorId) {
        try {
            actualOutputPartial = controller.patchActor(actorId, validActorUpdates);
        }
        catch (Exception ex) {
            caughtException = ex;
        }
    }

    @Then("an ActorResponse is returned")
    public void aActorResponseIsReturned() {
        assertNotNull(actualOutput);
        assertInstanceOf(ActorResponse.class, actualOutput);
    }

    @Then("a PartialActorResponse is returned")
    public void aPartialActorResponseIsReturned() {
        assertNotNull(actualOutputPartial);
        assertInstanceOf(PartialActorResponse.class, actualOutputPartial);
    }

    @Then("a ResponseStatusException is thrown")
    public void aResponseStatusExceptionIsThrown() {
        assertNotNull(caughtException);
        assertInstanceOf(ResponseStatusException.class, caughtException);
    }

    @Then("the actor is deleted")
    public void theActorIsDeleted() {
        verify(mockService, times(1)).deleteActor(anyShort());
    }

    @And("the status code is {int}")
    public void theStatusCodeIs(int statusCode) {
        assertEquals(statusCode, ((ResponseStatusException) caughtException).getStatusCode().value());
    }


}
