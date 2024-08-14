package com.example.sakila;

import com.example.sakila.language.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import static com.example.sakila.CommonStepDefs.caughtException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LanguageServiceStepDefs {

    LanguageRepository mockRepository = mock(LanguageRepository.class);
    LanguageInput languageInput = new LanguageInput();
    LanguageInput invalidLanguageInput = new LanguageInput();
    short currentLanguageId;
    LanguageResponse actualOutput;
    List<LanguageResponse> actualOutputList;
    List<Language> languageList;
    Map<String, Object> validLanguageUpdates = Map.of("name", "German");


    @Given("the database contains languages")
    public void theDatabaseContainsLanguages() {
        Language language1 = new Language();
        Language language2 = new Language();
        language1.setName("English");
        language2.setName("French");

        languageList = List.of(language1, language2);
        doReturn(languageList).when(mockRepository).findAll();
    }

    @Given("the database contains a language with ID {short}")
    public void theDatabaseContainsALanguageWithID(short langugageId) {
        Language language = new Language();
        language.setName("English");

        doReturn(java.util.Optional.of(language)).when(mockRepository).findById(langugageId);
    }

    @Given("the database does not contain a language with ID {short}")
    public void theDatabaseDoesNotContainALanguageWithID(short languageId) {
        doReturn(java.util.Optional.empty()).when(mockRepository).findById(languageId);
    }

    @Given("the service receives a valid LanguageInput")
    public void theServiceReceivesAValidLanguageInputRequestBody() {
        languageInput.setName("German");
        doReturn(new Language()).when(mockRepository).save(any());
    }

    @Given("the service receives an invalid LanguageInput")
    public void theServiceReceivesAnInvalidLanguageInputRequestBody() {
        languageInput = invalidLanguageInput;
        doThrow(new RuntimeException()).when(mockRepository).save(new Language());
    }

    @When("the service retrieves all languages")
    public void theServiceRetrievesAllLanguages() {
        LanguageService service = new LanguageService(mockRepository);
        actualOutputList = service.readAllLanguages();
    }

    @When("the service retrieves a language with ID {short}")
    public void theServiceRetrievesALanguageWithID(short languageId) {
        try {
            LanguageService service = new LanguageService(mockRepository);
            actualOutput = service.readLanguageById(languageId);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("the service creates a language")
    public void theServiceCreatesALanguage() {
        try {
            LanguageService service = new LanguageService(mockRepository);
            actualOutput = service.createLanguage(languageInput);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("the service updates a language with ID {short}")
    public void theServiceUpdatesALanguageWithID(short languageId) {
        try {
            LanguageService service = new LanguageService(mockRepository);
            actualOutput = service.updateLanguage(languageId, languageInput);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("the service patches a language with ID {short}")
    public void theServicePatchesALanguageWithID(short languageId) {
        try {
            LanguageService service = new LanguageService(mockRepository);
            actualOutput = service.patchLanguage(languageId, validLanguageUpdates);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @When("the service deletes a language with ID {short}")
    public void theServiceDeletesALanguageWithID(short languageId) {
        currentLanguageId = languageId;
        try {
            LanguageService service = new LanguageService(mockRepository);
            service.deleteLanguage(currentLanguageId);
        }
        catch (Exception e) {
            caughtException = e;
        }
    }

    @And("the service returns a list of LanguageResponse")
    public void theServiceReturnsAListOfLanguageResponse() {
        assertNotNull(actualOutputList);
        assertInstanceOf(List.class, actualOutputList);
        actualOutputList.forEach(languageResponse -> assertInstanceOf(LanguageResponse.class, languageResponse));
    }

    @And("the service returns a LanguageResponse")
    public void theServiceReturnsALanguageResponse() {
        assertNotNull(actualOutput);
        assertInstanceOf(LanguageResponse.class, actualOutput);
    }

    @And("the service deletes the language")
    public void theServiceDeletesTheLanguage() {
        verify(mockRepository, times(1)).deleteById(currentLanguageId);
    }
}
