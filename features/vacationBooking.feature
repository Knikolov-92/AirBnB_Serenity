Feature: Book a vacation

  @debug
  Scenario Outline: John books a vacation at a place somewhere
    Given John is on the Home page
    And John submits booking options:
      | where   | check-In_in   | check-Out_in   | adults   | kids   | babies   |
      | <where> | <check-In_in> | <check-Out_in> | <adults> | <kids> | <babies> |
    Then John should see the offers for the location
    When John filters his preferences
    And John picks the first 5-star-place
    Then John should see the summary of the reservation

    Examples:
      | where  | check-In_in | check-Out_in | adults | kids | babies |
      | Sicily | 5           | 12           | 2      | 1    | 0      |