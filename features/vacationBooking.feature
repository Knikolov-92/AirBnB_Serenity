Feature: Book a vacation

  @debug
  Scenario Outline: John books a vacation at a place somewhere
    Given John is on the Home page
    When John submits booking options:
      | where   | check-In_in   | check-Out_in   | adults   | kids   | babies   |
      | <where> | <check-In_in> | <check-Out_in> | <adults> | <kids> | <babies> |
    Then The offers for the location are displayed
    When John selects a currency "<currency>"
    And John filters his preferences:
     | price_min   | price_max   | bathroom   | air_conditioner   | hot_tube   |
     | <price_min> | <price_max> | <bathroom> | <air_conditioner> | <hot_tube> |
    And John picks the first "<rating>"-star-place:
    Then The summary of the reservation should be displayed

    Examples: primary booking options
      | where   | check-In_in | check-Out_in | adults | kids | babies | currency | price_min | price_max | bathroom | air_conditioner | hot_tube | rating |
      | Sicily  | 5           | 12           | 2      | 1    | 0      | EUR      | 50        | 100       | 1        | yes             | yes      | 5.0    |
   #   | Bali    | 7           | 27           | 2      | 3    | 3      | EUR      | 150       | 2500      | 2        | yes             | yes      | 5.0    |
   #   | Germany | 90          | 180          | 2      | 0    | 0      | EUR      | 199       | 999       | 1        | yes             | no       | 4.6   |

