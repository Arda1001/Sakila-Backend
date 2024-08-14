Feature: FilmService

  Scenario: Get all films
    Given the database contains films
    When the service retrieves all films
    And the service returns a list of FilmResponse

  Scenario: Get film by ID
    Given the database contains a film with ID 1
    When the service retrieves a film with ID 1
    And the service returns a FilmResponse

  Scenario: Get film by invalid ID
    Given the database does not contain a film with ID 120
    When the service retrieves a film with ID 120
    Then a RunTimeException is thrown

  Scenario: Create film
    Given the service receives a valid FilmInput
    When the service creates a film
    And the service returns an PartialFilmResponse

  Scenario: Create film with invalid request body
    Given the service receives an invalid FilmInput
    When the service creates a film
    Then a RunTimeException is thrown

  Scenario: Update film
    Given the database contains a film with ID 1
    And the service receives a valid FilmInput
    When the service updates a film with ID 1
    And the service returns an PartialFilmResponse

  Scenario: Update film with invalid ID
    Given the database does not contain a film with ID 120
    And the service receives a valid FilmInput
    When the service updates a film with ID 120
    Then a RunTimeException is thrown

  Scenario: Update film with invalid request body
    Given the database contains a film with ID 1
    And the service receives an invalid FilmInput
    When the service updates a film with ID 1
    Then a RunTimeException is thrown

  Scenario: Patch film
    Given the database contains a film with ID 1
    And the service receives a valid FilmInput
    When the service patches a film with ID 1
    And the service returns an PartialFilmResponse

  Scenario: Patch film with invalid request body
    Given the database contains a film with ID 1
    And the service receives an invalid FilmInput
    When the service patches a film with ID 1
    Then a RunTimeException is thrown

  Scenario: Delete film
    Given the database contains a film with ID 1
    When the service deletes a film with ID 1
    And the service deletes the film

  Scenario: Delete film with invalid ID
    Given the database does not contain a film with ID 120
    When the service deletes a film with ID 120
    Then a RunTimeException is thrown