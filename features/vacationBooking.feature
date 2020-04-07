Feature: Book a vacation

  @debug
  Scenario Outline: John books a vacation at a place somewhere
    Given John is on the Home page
    And John submits booking options:
      | where   | check-In_in   | check-Out_in   | adults   | kids   | babies   |
      | <where> | <check-In_in> | <check-Out_in> | <adults> | <kids> | <babies> |
    Then John should see the offers for the location: "<where>"
    When John selects a currency "<currency>"
    And John filters his preferences:
     | price_min   | price_max   | bathroom   | air_conditioner   | hot_tube   |
     | <price_min> | <price_max> | <bathroom> | <air_conditioner> | <hot_tube> |
    And John picks the first x-star-place: "<star_level>"
    Then John should see the summary of the reservation

    Examples:
      | where  | check-In_in | check-Out_in | adults | kids | babies | currency | price_min | price_max | bathroom | air_conditioner | hot_tube | star_level |
      | Sicily | 5           | 12           | 2      | 1    | 0      | EUR      | 50        | 100       | 1        | yes             | yes      | 5.0        |
