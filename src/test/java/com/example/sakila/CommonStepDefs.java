package com.example.sakila;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.springframework.web.server.ResponseStatusException;


import static org.junit.jupiter.api.Assertions.*;

public class CommonStepDefs {

    static Exception caughtException;

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
