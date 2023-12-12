package fr.imt.coffee.machine.cucumber.steps;

import fr.imt.coffee.cupboard.coffee.type.CoffeeType;
import fr.imt.coffee.cupboard.container.*;
import fr.imt.coffee.cupboard.exception.CupNotEmptyException;
import fr.imt.coffee.machine.EspressoCoffeeMachine;
import fr.imt.coffee.machine.exception.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class CucumberStepsExpressoCoffeeMachineTest {
    public EspressoCoffeeMachine esspressoMachine;
    public Mug mug;
    public Cup cup;
    public CoffeeContainer containerWithCoffee;

    @Given("an espresso coffee machine with {double} l of min capacity, {double} l of max capacity, {double} l per h of water flow for the pump")
    public void givenACoffeeMachine(double minimalWaterCapacity, double maximalWaterCapacity, double pumpWaterFlow){
        esspressoMachine = new EspressoCoffeeMachine(minimalWaterCapacity, maximalWaterCapacity, minimalWaterCapacity, maximalWaterCapacity, pumpWaterFlow);
    }

    @And("a {string} with a capacity of {double} for the espresso coffee")
    public void aWithACapacityOfForTheEspresso(String containerType, double containerCapacity) {
        if ("mug".equals(containerType))
            mug = new Mug(containerCapacity);
        if ("cup".equals(containerType))
            cup = new Cup(containerCapacity);
    }

    @When("I plug the espresso machine to electricity")
    public void iPlugTheEspressoMachineToElectricity() {
        esspressoMachine.plugToElectricalPlug();
    }

    @And("I add {double} liter of water in the water tank of the espresso")
    public void iAddLitersOfWaterEspresso(double waterVolume) {
        esspressoMachine.addWaterInTank(waterVolume);
    }

    @And("I add {double} liter of {string} in the bean tank of the espresso")
    public void iAddLitersOfCoffeeBeansForEspresso(double beanVolume, String coffeeType) throws BeanTypeDifferentOfCoffeeTypeTankException {
        esspressoMachine.addCoffeeInBeanTank(beanVolume, fr.imt.coffee.cupboard.coffee.type.CoffeeType.valueOf(coffeeType));
    }

    @And("I made an espresso coffee {string}")
    public void iMadeAnEspressoCoffee(String coffeeType) throws InterruptedException, CupNotEmptyException, LackOfWaterInTankException, MachineNotPluggedException, CoffeeTypeCupDifferentOfCoffeeTypeTankException, CannotMakeCremaWithSimpleCoffeeMachine {
        //On créé un mock de l'objet random
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        //On vient ensuite stubber la méthode nextGaussian pour pouvoir controler la valeur retournée
        //ici on veut qu'elle retourne 0.6
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
        //On injecte ensuite le mock créé dans la machine à café
        esspressoMachine.setRandomGenerator(randomMock);

        if (mug != null)
            containerWithCoffee = esspressoMachine.makeACoffee(mug, CoffeeType.valueOf(coffeeType));
        if (cup != null)
            containerWithCoffee = esspressoMachine.makeACoffee(cup, CoffeeType.valueOf(coffeeType));

    }

    @Then("the espresso coffee machine return a coffee mug not empty")
    public void theEspressoCoffeeMachineReturnACoffeeMugNotEmpty() {
        Assertions.assertFalse(containerWithCoffee.isEmpty());
    }


    @And("an espresso coffee volume equals to {double}")
    public void anEspressoCoffeeVolumeEqualsTo(double coffeeVolume) {
        assertThat(coffeeVolume, is(containerWithCoffee.getCapacity()));
    }

    @And("an espresso coffee {string} containing a coffee type {string}")
    public void anEspressoCoffeeMugContainingACoffeeType(String containerType, String coffeeType) {
        if ("mug".equals(containerType))
            assertThat(containerWithCoffee, instanceOf(CoffeeMug.class));
        if ("cup".equals(containerType))
            assertThat(containerWithCoffee, instanceOf(CoffeeCup.class));

        assertThat(containerWithCoffee.getCoffeeType(), is(CoffeeType.valueOf(coffeeType)));
    }


}
