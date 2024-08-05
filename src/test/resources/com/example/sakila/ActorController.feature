Feature: ActorController

  Scenario: An actor is read by ID
    Given an actor exists with ID 42
    When a GET request is made for an actor with ID 42
    Then a ActorResponse is returned

  Scenario: An invalid actor is read by ID
    Given an actor does not exist with ID 120
    When a GET request is made for an actor with ID 120
    Then a ResponseStatusException is thrown
    And the status code is 404

  Scenario: An actor is created
    Given a valid ActorInput request body
    When a POST request is made to the actors collection
    Then a ActorResponse is returned
    And the status code is 204