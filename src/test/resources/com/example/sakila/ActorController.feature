Feature: ActorController

  Scenario: An actor is read by ID
    Given an actor exists with ID 42
    When a GET request is made for an actor with ID 42
    Then an ActorResponse is returned

  Scenario: An invalid actor is read by ID
    Given an actor does not exist with ID 120
    When a GET request is made for an actor with ID 120
    Then a ResponseStatusException is thrown
    And the status code is 404

  Scenario: An actor is created
    Given a valid ActorInput request body
    When a POST request is made to the actors collection
    Then a PartialActorResponse is returned

  Scenario: An actor could not be created
    Given an invalid ActorInput request body
    When a POST request is made to the actors collection
    Then a ResponseStatusException is thrown
    And the status code is 400

  Scenario: An actor is updated
    Given an actor exists with ID 21
    And a valid ActorInput request body
    When a PUT request is made for an actor with ID 21
    Then a PartialActorResponse is returned

  Scenario: An actor could not be updated
    Given an actor does not exist with ID 120
    And an invalid ActorInput request body
    When a PUT request is made for an actor with ID 120
    Then a ResponseStatusException is thrown
    And the status code is 404

  Scenario: an actor is updated with an invalid request body
    Given an actor exists with ID 34
    And an invalid ActorInput request body
    When a PUT request is made for an actor with ID 34
    Then a ResponseStatusException is thrown

  Scenario: am actor is patched
    Given an actor exists with ID 45
    And a valid ActorInput request body
    When a PATCH request is made for an actor with ID 45
    Then a PartialActorResponse is returned

  Scenario: an actor is patched with an invalid request body
    Given an actor exists with ID 50
    And an invalid ActorInput request body
    When a PATCH request is made for an actor with ID 50
    Then a ResponseStatusException is thrown
    And the status code is 400

  Scenario: An actor is deleted
    Given an actor exists with ID 23
    When a DELETE request is made for an actor with ID 23
    Then the actor is deleted

  Scenario: An actor could not be deleted
    Given an actor does not exist with ID 120
    When a DELETE request is made for an actor with ID 120
    Then a ResponseStatusException is thrown
    And the status code is 404