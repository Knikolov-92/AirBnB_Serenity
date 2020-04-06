Feature: Book a vacation

  Scenario: John books a vacation at a place somewhere
    Given John is on the Home page
    And John submits booking options:
      | Where  | Check-In_in | Check-Out_in | Adults | kids | babies |
      | Sicily | 5           | 12           | 2      | 1    | 0      |
    Then John should see the offers for the location
    When John filters his preferences
    And John picks the first 5-star-place
    Then John should see the summary of the reservation