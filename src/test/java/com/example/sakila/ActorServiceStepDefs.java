package com.example.sakila;

import com.example.sakila.actor.*;
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

public class ActorServiceStepDefs {

    ActorRepository mockRepository = mock(ActorRepository.class);
    ActorInput actorInput = new ActorInput();
    ActorInput invalidActorInput = new ActorInput();
    short currentActorId;
    ActorResponse actualOutput;
    PartialActorResponse actualOutputPartial;
    List<ActorResponse> actualOutputList;
    List<Actor> actorList;
    Map<String, Object> validActorUpdates = Map.of("firstName", "John", "lastName", "Doe");


    @Given("the database contains actors")
    public void theDatabaseContainsActors() {
        Actor actor1 = new Actor();
        Actor actor2 = new Actor();
        actor1.setFirstName("John");
        actor1.setLastName("Doe");
        actor2.setFirstName("Jane");
        actor2.setLastName("Doe");

        actorList = List.of(actor1, actor2);
        doReturn(actorList).when(mockRepository).findAll();
    }

    @Given("the database contains an actor with ID {short}")
    public void theDatabaseContainsAnActorWithID(short actorId) {
        Actor actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Doe");

        doReturn(java.util.Optional.of(actor)).when(mockRepository).findById(actorId);
    }

    @Given("the database does not contain an actor with ID {short}")
    public void theDatabaseDoesNotContainAnActorWithID(short actorId) {
        doReturn(java.util.Optional.empty()).when(mockRepository).findById(actorId);
    }

    @Given("the service receives a valid ActorInput")
    public void theServiceReceivesAValidActorInput() {
        actorInput.setFirstName("John");
        actorInput.setLastName("Doe");
        doReturn(new Actor()).when(mockRepository).save(any());
    }

    @Given("the service receives an invalid ActorInput")
    public void theServiceReceivesAnInvalidActorInput() {
        actorInput = invalidActorInput;
        doThrow(new RuntimeException()).when(mockRepository).save(new Actor());
    }

    @When("the service retrieves all actors")
    public void theServiceRetrievesAllActors() {
        ActorService actorService = new ActorService(mockRepository);
        actualOutputList = actorService.readAllActors();
    }

    @When("the service retrieves an actor with ID {short}")
    public void theServiceRetrievesAnActorWithID(short actorId) {
        try {
            ActorService actorService = new ActorService(mockRepository);
            actualOutput = actorService.readActorById(actorId);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("the service creates an actor")
    public void theServiceCreatesAnActor() {
        try {
            ActorService actorService = new ActorService(mockRepository);
            actualOutputPartial = actorService.createActor(actorInput);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("the service updates an actor with ID {short}")
    public void theServiceUpdatesAnActorWithID(short actorId) {
        try {
            ActorService actorService = new ActorService(mockRepository);
            actualOutputPartial = actorService.updateActor(actorInput, actorId);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("the service patches an actor with ID {short}")
    public void theServicePatchesAnActorWithID(short actorId) {
        try {
            ActorService actorService = new ActorService(mockRepository);
            actualOutputPartial = actorService.patchActor(actorId, validActorUpdates);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("the service deletes an actor with ID {short}")
    public void theServiceDeletesAnActorWithID(short actorId) {
        currentActorId = actorId;
        try {
            ActorService actorService = new ActorService(mockRepository);
            actorService.deleteActor(actorId);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @And("the service returns a list of ActorResponse")
    public void theServiceReturnsAListOfActorResponse() {
        assertNotNull(actualOutputList);
        assertInstanceOf(List.class, actualOutputList);
        actualOutputList.forEach(actorResponse -> assertInstanceOf(ActorResponse.class, actorResponse));
    }

    @And("the service returns an ActorResponse")
    public void theServiceReturnsAnActorResponse() {
        assertNotNull(actualOutput);
        assertInstanceOf(ActorResponse.class, actualOutput);
    }

    @And("the service returns an PartialActorResponse")
    public void theServiceReturnsAnPartialActorResponse() {
        assertNotNull(actualOutputPartial);
        assertInstanceOf(PartialActorResponse.class, actualOutputPartial);
    }

    @And("the service deletes the actor")
    public void theServiceDeletesTheActor() {
        verify(mockRepository, times(1)).deleteById(currentActorId);
    }
}
