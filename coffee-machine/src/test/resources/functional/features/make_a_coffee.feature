Feature: Make a coffee with a complete coffee machine
  A user want a coffee
  Scenario: A user plug the coffee machine and make a coffee Arabica
    Given a coffee machine with 0.10 l of min capacity, 3.0 l of max capacity, 600.0 l per h of water flow for the pump
    And a "mug" with a capacity of 0.25
    When I plug the machine to electricity
    And I add 1 liter of water in the water tank
    And I add 0.5 liter of "ARABICA" in the bean tank
    And I made a coffee "ARABICA"
    Then the coffee machine return a coffee mug not empty
    And a coffee volume equals to 0.25
    And a coffee "mug" containing a coffee type "ARABICA"


  Scenario: A user plug the coffee machine and make a coffee
    Given a coffee machine with 0.10 l of min capacity, 3.0 l of max capacity, 600.0 l per h of water flow for the pump
    And a "cup" with a capacity of 0.15
    When I plug the machine to electricity
    And I add 1 liter of water in the water tank
    And I add 0.5 liter of "ROBUSTA" in the bean tank
    And I made a coffee "ROBUSTA"
    Then the coffee machine return a coffee mug not empty
    And a coffee volume equals to 0.15
    And a coffee "cup" containing a coffee type "ROBUSTA"

  Scenario: A user plug the coffee machine
    Given a coffee machine with 0.2 l of min capacity, 4.0 l of max capacity, 500.0 l per h of water flow for the pump
    When I plug the machine to electricity
    Then the coffee machine is plugged

  Scenario: A user plug the espresso machine and make an espresso coffee
    Given an espresso coffee machine with 0.0 l of min capacity, 2.0 l of max capacity, 900.0 l per h of water flow for the pump
    And a "cup" with a capacity of 0.2 for the espresso coffee
    When I plug the espresso machine to electricity
    And I add 1 liter of water in the water tank of the espresso
    And I add 0.5 liter of "ARABICA_CREMA" in the bean tank of the espresso
    And I made an espresso coffee "ARABICA_CREMA"
    Then the espresso coffee machine return a coffee mug not empty
    And an espresso coffee volume equals to 0.2
    And an espresso coffee "cup" containing a coffee type "ARABICA_CREMA"
