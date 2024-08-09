Feature: LanguageService

  Scenario: Get all languages
    Given the database contains languages
    When the service retrieves all languages
    And the service returns a list of LanguageResponse

  Scenario: Get language by ID
    Given the database contains a language with ID 1
    When the service retrieves a language with ID 1
    And the service returns a LanguageResponse

  Scenario: Get language by invalid ID
    Given the database does not contain a language with ID 120
    When the service retrieves a language with ID 120
    Then a RunTimeException is thrown

  Scenario: Create language
    Given the service receives a valid LanguageInput
    When the service creates a language
    And the service returns a LanguageResponse

  Scenario: Create language with invalid request body
    Given the service receives an invalid LanguageInput
    When the service creates a language
    Then a RunTimeException is thrown

  Scenario: Update language
    Given the database contains a language with ID 1
    And the service receives a valid LanguageInput
    When the service updates a language with ID 1
    And the service returns a LanguageResponse

  Scenario: Update language with invalid ID
    Given the database does not contain a language with ID 120
    And the service receives a valid LanguageInput
    When the service updates a language with ID 120
    Then a RunTimeException is thrown

  Scenario: Update language with invalid request body
    Given the database contains a language with ID 1
    And the service receives an invalid LanguageInput
    When the service updates a language with ID 1
    Then a RunTimeException is thrown

  Scenario: Patch language
    Given the database contains a language with ID 1
    And the service receives a valid LanguageInput
    When the service patches a language with ID 1
    And the service returns a LanguageResponse

  Scenario: Patch language with invalid request body
    Given the database contains a language with ID 1
    And the service receives an invalid LanguageInput
    When the service patches a language with ID 1
    Then a RunTimeException is thrown

  Scenario: Delete language
    Given the database contains a language with ID 1
    When the service deletes a language with ID 1
    And the service deletes the language

  Scenario: Delete language with invalid ID
    Given the database does not contain a language with ID 120
    When the service deletes a language with ID 120
    Then a RunTimeException is thrown
