Feature: FilmController

  Scenario: Films are read
    Given films exists
    When a GET request is made for all films
    Then a list of FilmResponses is returned

  Scenario: A film is read by ID
    Given a film exists with ID 42
    When a GET request is made for a film with ID 42
    Then a FilmResponse is returned

  Scenario: An invalid film is read by ID
    Given a film does not exist with ID 120
    When a GET request is made for a film with ID 120
    Then a ResponseStatusException is thrown
    And the status code is 404

  Scenario: A film is created
    Given a valid FilmInput request body
    When a POST request is made to the films collection
    Then a PartialFilmResponse is returned

  Scenario: A film could not be created
    Given an invalid FilmInput request body
    When a POST request is made to the films collection
    Then a ResponseStatusException is thrown
    And the status code is 400

  Scenario: A film is updated
    Given a film exists with ID 21
    And a valid FilmInput request body
    When a PUT request is made for a film with ID 21
    Then a PartialFilmResponse is returned

  Scenario: A film could not be updated
    Given a film does not exist with ID 120
    And an invalid FilmInput request body
    When a PUT request is made for a film with ID 120
    Then a ResponseStatusException is thrown
    And the status code is 404

  Scenario: A film is updated with an invalid request body
    Given a film exists with ID 34
    And an invalid FilmInput request body
    When a PUT request is made for a film with ID 34
    Then a ResponseStatusException is thrown

  Scenario: A film is patched
    Given a film exists with ID 45
    And a valid FilmInput request body
    When a PATCH request is made for a film with ID 45
    Then a PartialFilmResponse is returned

  Scenario: A film is patched with an invalid request body
    Given a film exists with ID 50
    And an invalid FilmInput request body
    When a PATCH request is made for a film with ID 50
    Then a ResponseStatusException is thrown
    And the status code is 400

  Scenario: A film is deleted
    Given a film exists with ID 42
    When a DELETE request is made for a film with ID 42
    Then the film is deleted

  Scenario: A film could not be deleted
    Given a film does not exist with ID 120
    When a DELETE request is made for a film with ID 120
    Then a ResponseStatusException is thrown
    And the status code is 404