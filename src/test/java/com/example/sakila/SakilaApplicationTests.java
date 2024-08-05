package com.example.sakila;

import com.example.sakila.actor.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static org.mockito.Mockito.*;

@SpringBootTest
class SakilaApplicationTests {


	private ActorService mockService;
	private ActorController actorController;
	private ActorResponseController actorResponseController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockService = mock(ActorService.class);
		actorController = new ActorController(mockService);
		actorResponseController = new ActorResponseController(mockService);
		final var actor = new Actor((short) 1, "ARDA", "ORDU", Set.of());
		final var actorResponse = new ActorResponse(actor);
		doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).readActorById(any());
		doReturn(actorResponse).when(mockService).readActorById((short)1);
	}


	@Test
	public void shouldGiveActorDetailsWhenYouGetActorById() {
		final var expectedId = (short) 1;
		final var expectedFirstName = "ARDA";
		final var expectedLastName = "ORDU";

		final var actor = actorResponseController.readActorById(expectedId);

		Assertions.assertEquals(expectedId, actor.getId());
		Assertions.assertEquals(expectedFirstName, actor.getFirstName());
		Assertions.assertEquals(expectedLastName,actor.getLastName());
	}

	@Test
	public void actorResponseControllerReadActorByIdThrows404WhenInvalidId() {
		Exception exception = null;

		try {
			actorResponseController.readActorById((short) 2);
		}
		catch (Exception e) {
			exception = e;
		}

		Assertions.assertNotNull(exception);
		Assertions.assertInstanceOf(ResponseStatusException.class, exception);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, ((ResponseStatusException)exception).getStatusCode());
	}

	@Test
	public void shouldCreateActorSuccessfully() {

		final var actorInput = new ActorInput();
		final var expectedActor = new Actor((short) 1, "JOHN", "DOE", Set.of());
		when(mockService.createActor(any(ActorInput.class))).thenReturn(new PartialActorResponse(expectedActor));

		final var createdActor = actorController.create(actorInput);

		Assertions.assertNotNull(createdActor);
		Assertions.assertEquals(expectedActor.getId(), createdActor.getId());
		Assertions.assertEquals(expectedActor.getFirstName(), createdActor.getFirstName());
		Assertions.assertEquals(expectedActor.getLastName(), createdActor.getLastName());

		verify(mockService, times(1)).createActor(actorInput);
	}

	@Test
	public void shouldUpdateActorSuccessfully() {

		final var actorInput = new ActorInput();
		actorInput.setFirstName("JANE");
		actorInput.setLastName("DOE");

		final var expectedActor = new Actor((short) 1, "JANE", "DOE", Set.of());
		when(mockService.updateActor(any(ActorInput.class), any(Short.class))).thenReturn(new PartialActorResponse(expectedActor));

		final var updatedActor = actorController.update((short) 1, actorInput);

		Assertions.assertNotNull(updatedActor);
		Assertions.assertEquals(expectedActor.getId(), updatedActor.getId());
		Assertions.assertEquals(expectedActor.getFirstName(), updatedActor.getFirstName());
		Assertions.assertEquals(expectedActor.getLastName(), updatedActor.getLastName());

		verify(mockService, times(1)).updateActor(actorInput, (short) 1);
	}

	@Test
	public void actorResponseControllerUpdateActorByIdThrows404WhenInvalidId() {
		doThrow(new RuntimeException("Actor not found with id: 2")).when(mockService).updateActor(any(ActorInput.class), eq((short) 2));
		Exception exception = null;

		try {
			final var actorInput = new ActorInput();
			actorController.update((short) 2, actorInput);
		}
		catch (Exception e) {
			exception = e;
		}

		Assertions.assertNotNull(exception);
		Assertions.assertInstanceOf(RuntimeException.class, exception);
		Assertions.assertEquals("Actor not found with id: 2", exception.getMessage());
	}

}
