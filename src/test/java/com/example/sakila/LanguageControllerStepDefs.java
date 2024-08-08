package com.example.sakila;

import com.example.sakila.language.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import static com.example.sakila.CommonStepDefs.caughtException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LanguageControllerStepDefs {

    LanguageService mockService = mock(LanguageService.class);
    LanguageController controller = new LanguageController(mockService);
    LanguageInput languageInput = new LanguageInput();
    LanguageInput invalidLanguageInput = new LanguageInput();
    Map<String, Object> validLanguageUpdates = Map.of("name", "English");

    LanguageResponse actualOutput;
    List<LanguageResponse> actualOutputList;
    short currentLanguageId;

    @Given("languages exist")
    public void languagesExist() {
        Language language1 = new Language();
        Language language2 = new Language();
        language1.setName("English");
        language2.setName("French");

        LanguageResponse languageResponse1 = new LanguageResponse(language1);
        LanguageResponse languageResponse2 = new LanguageResponse(language2);

        actualOutputList = List.of(languageResponse1, languageResponse2);
        doReturn(actualOutputList).when(mockService).readAllLanguages();

    }

    @Given("a language does not exist with ID {short}")
    public void aLanguageDoesNotExistWithID(short languageId) {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).readLanguageById(languageId);
        doThrow( new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).updateLanguage(eq(languageId), any());
        doThrow( new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).deleteLanguage(languageId);
        doThrow( new ResponseStatusException(HttpStatus.NOT_FOUND)).when(mockService).patchLanguage(eq(languageId), any());
    }

    @Given("a valid LanguageInput request body")
    public void aValidLanguageInputRequestBody() {
        languageInput.setName("English");
        doReturn( new LanguageResponse(new Language())).when(mockService).createLanguage(languageInput);
        doReturn( new LanguageResponse(new Language())).when(mockService).updateLanguage(currentLanguageId, languageInput);
        doReturn( new LanguageResponse(new Language())).when(mockService).patchLanguage(currentLanguageId, validLanguageUpdates);
    }

    @Given("an invalid LanguageInput request body")
    public void anInvalidLanguageInputRequestBody() {
        languageInput = invalidLanguageInput;
        doThrow( new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(mockService).createLanguage(languageInput);
        doThrow( new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(mockService).updateLanguage(currentLanguageId, languageInput);
        doThrow( new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(mockService).patchLanguage(eq(currentLanguageId), any());
    }

    @Given("a language exists with ID {short}")
    public void aLanguageExistsWithID(short languageId) {
        Language language = new Language();
        language.setName("German");
        currentLanguageId = languageId;
        doReturn( new LanguageResponse(language)).when(mockService).readLanguageById(currentLanguageId);
    }

    @When("a GET request is made for all languages")
    public void aGETRequestIsMadeForAllLanguages() {
        try {
            actualOutputList = controller.readAllLanguages();
        }
        catch (Exception e) {
           caughtException = e;
        }
    }

    @When("a GET request is made for a language with ID {short}")
    public void aGETRequestIsMadeForALanguageWithID(short languageId) {
        try {
            actualOutput = controller.readLanguageById(languageId);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("a POST request is made to the languages collection")
    public void aPOSTRequestIsMadeToTheLanguagesCollection() {
        try {
            actualOutput = controller.createLanguage(languageInput);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("a PUT request is made for a language with ID {short}")
    public void aPUTRequestIsMadeForALanguageWithID(short languageId) {
        try {
            actualOutput = controller.updateLanguage(languageId, languageInput);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("a PATCH request is made for a language with ID {short}")
    public void aPATCHRequestIsMadeForALanguageWithID(short languageId) {
        try {
            actualOutput = controller.patchLanguage(languageId, validLanguageUpdates);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("a DELETE request is made for a language with ID {short}")
    public void aDELETERequestIsMadeForALanguageWithID(short languageId) {
        try {
            controller.deleteLanguage(languageId);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @Then("a list of LanguageResponses is returned")
    public void aListOfLanguageResponsesIsReturned() {
        assertNotNull(actualOutputList);
        assertInstanceOf(List.class, actualOutputList);
        actualOutputList.forEach(response -> assertInstanceOf(LanguageResponse.class, response));
    }



    @Then("a LanguageResponse is returned")
    public void aLanguageResponseIsReturned() {
        assertNotNull(actualOutput);
        assertInstanceOf(LanguageResponse.class, actualOutput);
    }



    @Then("the language is deleted")
    public void theLanguageIsDeleted() {
        verify(mockService, times(1)).deleteLanguage(currentLanguageId);
    }
}
