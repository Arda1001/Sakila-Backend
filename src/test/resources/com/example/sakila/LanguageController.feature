Feature: LanguageController

  Scenario: Languages are read
    Given languages exist
    When a GET request is made for all languages
    Then a list of LanguageResponses is returned

  Scenario: A language is read by ID
    Given a language exists with ID 1
    When a GET request is made for a language with ID 1
    Then a LanguageResponse is returned

  Scenario: An invalid language is read by ID
    Given a language does not exist with ID 120
    When a GET request is made for a language with ID 120
    Then a ResponseStatusException is thrown
    And the status code is 404

  Scenario: A language is created
    Given a valid LanguageInput request body
    When a POST request is made to the languages collection
    Then a LanguageResponse is returned

  Scenario: A language could not be created
    Given an invalid LanguageInput request body
    When a POST request is made to the languages collection
    Then a ResponseStatusException is thrown
    And the status code is 400

  Scenario: A language is updated
    Given a language exists with ID 1
    And a valid LanguageInput request body
    When a PUT request is made for a language with ID 1
    Then a LanguageResponse is returned

  Scenario: A language could not be updated
    Given a language does not exist with ID 120
    And an invalid LanguageInput request body
    When a PUT request is made for a language with ID 120
    Then a ResponseStatusException is thrown
    And the status code is 404

  Scenario: a language is updated with an invalid request body
    Given a language exists with ID 1
    And an invalid LanguageInput request body
    When a PUT request is made for a language with ID 1
    Then a ResponseStatusException is thrown

  Scenario: a language is patched
    Given a language exists with ID 1
    And a valid LanguageInput request body
    When a PATCH request is made for a language with ID 1
    Then a LanguageResponse is returned

  Scenario: a language is patched with an invalid request body
    Given a language exists with ID 1
    And an invalid LanguageInput request body
    When a PATCH request is made for a language with ID 1
    Then a ResponseStatusException is thrown
    And the status code is 400

  Scenario: a language is deleted
    Given a language exists with ID 1
    When a DELETE request is made for a language with ID 1
    Then the language is deleted

  Scenario: a language could not be deleted
    Given a language does not exist with ID 120
    When a DELETE request is made for a language with ID 120
    Then a ResponseStatusException is thrown
    And the status code is 404