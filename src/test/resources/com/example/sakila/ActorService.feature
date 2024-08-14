Feature: ActorService

  Scenario: Get all actors
    Given the database contains actors
    When the service retrieves all actors
    And the service returns a list of ActorResponse

  Scenario: Get actor by ID
    Given the database contains an actor with ID 1
    When the service retrieves an actor with ID 1
    And the service returns an ActorResponse

  Scenario: Get actor by invalid ID
    Given the database does not contain an actor with ID 120
    When the service retrieves an actor with ID 120
    Then a RunTimeException is thrown

  Scenario: Create actor
    Given the service receives a valid ActorInput
    When the service creates an actor
    And the service returns an PartialActorResponse

  Scenario: Create actor with invalid request body
    Given the service receives an invalid ActorInput
    When the service creates an actor
    Then a RunTimeException is thrown

  Scenario: Update actor
    Given the database contains an actor with ID 1
    And the service receives a valid ActorInput
    When the service updates an actor with ID 1
    And the service returns an PartialActorResponse

  Scenario: Update actor with invalid ID
    Given the database does not contain an actor with ID 120
    And the service receives a valid ActorInput
    When the service updates an actor with ID 120
    Then a RunTimeException is thrown

  Scenario: Update actor with invalid request body
    Given the database contains an actor with ID 1
    And the service receives an invalid ActorInput
    When the service updates an actor with ID 1
    Then a RunTimeException is thrown

  Scenario: Patch actor
    Given the database contains an actor with ID 1
    And the service receives a valid ActorInput
    When the service patches an actor with ID 1
    And the service returns an PartialActorResponse

  Scenario: Patch actor with invalid request body
    Given the database contains an actor with ID 1
    And the service receives an invalid ActorInput
    When the service patches an actor with ID 1
    Then a RunTimeException is thrown

  Scenario: Delete actor
    Given the database contains an actor with ID 1
    When the service deletes an actor with ID 1
    And the service deletes the actor

  Scenario: Delete actor with invalid ID
    Given the database does not contain an actor with ID 120
    When the service deletes an actor with ID 120
    Then a RunTimeException is thrown