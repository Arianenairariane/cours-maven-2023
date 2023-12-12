package fr.imt.coffee.machine;

import fr.imt.coffee.cupboard.coffee.type.CoffeeType;
import fr.imt.coffee.cupboard.container.*;
import fr.imt.coffee.cupboard.exception.CupNotEmptyException;
import fr.imt.coffee.machine.component.WaterTank;
import fr.imt.coffee.machine.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class CoffeeMachineUnitTest {
    public CoffeeMachine coffeeMachineUnderTest;

    /**
     * @BeforeEach est une annotation permettant d'exécuter la méthode annotée avant chaque test unitaire
     * Ici avant chaque test on initialise la machine à café
     */
    @BeforeEach
    public void beforeTest(){
        coffeeMachineUnderTest = new CoffeeMachine(
                0,10,
                0,10,  700);
    }

    /**
     * On vient tester si la machine ne se met pas en défaut
     */
    @Test
    public void testMachineFailureTrue(){
        //On créé un mock de l'objet random
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        //On vient ensuite stubber la méthode nextGaussian pour pouvoir contrôler la valeur retournée
        //ici on veut qu'elle retourne 1.0
        //when : permet de définir quand sur quelle méthode établir le stub
        //thenReturn : va permettre de contrôler la valeur retournée par le stub
        Mockito.when(randomMock.nextGaussian()).thenReturn(1.0);
        //On injecte ensuite le mock créé dans la machine à café
        coffeeMachineUnderTest.setRandomGenerator(randomMock);

        //On vérifie que le booleen outOfOrder est bien à faux avant d'appeler la méthode
        Assertions.assertFalse(coffeeMachineUnderTest.isOutOfOrder());
        //Ou avec Hamcrest
        assertThat(false, is(coffeeMachineUnderTest.isOutOfOrder()));

        //on appelle la méthode qui met la machine en défaut
        //On a mocké l'objet random donc la valeur retournée par nextGaussian() sera 1
        //La machine doit donc se mettre en défaut
        coffeeMachineUnderTest.coffeeMachineFailure();

        Assertions.assertTrue(coffeeMachineUnderTest.isOutOfOrder());
        assertThat(true, is(coffeeMachineUnderTest.isOutOfOrder()));
    }

    /**
     * On vient tester si la machine se met en défaut
     */
    @Test
    public void testMachineFailureFalse(){
        //On créé un mock de l'objet random
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        //On vient ensuite stubber la méthode nextGaussian pour pouvoir contrôler la valeur retournée
        //ici on veut qu'elle retourne 0.6
        //when : permet de définir quand sur quelle méthode établir le stub
        //thenReturn : va permettre de contrôler la valeur retournée par le stub
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
        //On injecte ensuite le mock créé dans la machine à café
        coffeeMachineUnderTest.setRandomGenerator(randomMock);

        //On vérifie que le booleen outOfOrder est bien à faux avant d'appeler la méthode
        Assertions.assertFalse(coffeeMachineUnderTest.isOutOfOrder());
        //Ou avec Hamcrest
        assertThat(false, is(coffeeMachineUnderTest.isOutOfOrder()));

        //on appelle la méthode qui met la machine en défaut
        //On a mocker l'objet random donc la valeur retournée par nextGaussian() sera 0.6
        //La machine doit donc NE PAS se mettre en défaut
        coffeeMachineUnderTest.coffeeMachineFailure();

        Assertions.assertFalse(coffeeMachineUnderTest.isOutOfOrder());
        //Ou avec Hamcrest
        assertThat(false, is(coffeeMachineUnderTest.isOutOfOrder()));
    }

    /**
     * On test que la machine se branche correctement au réseau électrique
     */
    @Test
    public void testPlugMachine(){
        Assertions.assertFalse(coffeeMachineUnderTest.isPlugged());

        coffeeMachineUnderTest.plugToElectricalPlug();

        Assertions.assertTrue(coffeeMachineUnderTest.isPlugged());
    }

    /**
     * On test qu'une exception est bien levée lorsque que le cup passé en paramètre retourne qu'il n'est pas vide
     * Tout comme le test sur la mise en défaut afin d'avoir un comportement isolé et indépendant de la machine
     * on vient ici mocker un objet Cup afin d'en maitriser complétement son comportement
     * On ne compte pas sur "le bon fonctionnement de la méthode"
     */
    @Test
    public void testMakeACoffeeCupNotEmptyException(){
        //Empêche la machine d'être hors service
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
        coffeeMachineUnderTest.setRandomGenerator(randomMock);

        Cup mockCup = Mockito.mock(Cup.class);
        Mockito.when(mockCup.isEmpty()).thenReturn(false);
        Mockito.when(mockCup.getCapacity()).thenReturn(3.0);

        coffeeMachineUnderTest.plugToElectricalPlug();
        coffeeMachineUnderTest.addWaterInTank(4.0);


        //assertThrows( [Exception class expected], [lambda expression with the method that throws an exception], [exception message expected])
        //AssertThrows va permettre de venir tester la levée d'une exception, ici lorsque que le contenant passé en
        //paramètre n'est pas vide
        //On teste à la fois le type d'exception levée mais aussi le message de l'exception
        Assertions.assertThrows(CupNotEmptyException.class, ()->{
                coffeeMachineUnderTest.makeACoffee(mockCup, CoffeeType.MOKA);
            });
    }

    /**
     * On teste que si on n'a pas suffisamment d'eau dans le réservior (moins que le capacité du contenant),
     * on lève une exception
     */
    @Test
    public void testMakeACoffeeLackOfWaterInTankException(){
        //Empêche la machine d'être hors service
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
        coffeeMachineUnderTest.setRandomGenerator(randomMock);

        Cup mockCup = Mockito.mock(Cup.class);
        Mockito.when(mockCup.isEmpty()).thenReturn(false);
        Mockito.when(mockCup.getCapacity()).thenReturn(3.0);

        coffeeMachineUnderTest.plugToElectricalPlug();

        //Initially no water in waterTank
        WaterTank waterTank = coffeeMachineUnderTest.getWaterTank();
        assertEquals(waterTank.getActualVolume(),waterTank.getMinVolume());

        assertThrows(LackOfWaterInTankException.class,()->{
            coffeeMachineUnderTest.makeACoffee(mockCup,CoffeeType.BAHIA);
        });
    }

    /**On teste que si le type de café en entrée n'est pas le même que cleui dans le réservoir,
     * on lève une exception
     * @throws BeanTypeDifferentOfCoffeeTypeTankException
     * @throws CoffeeTypeCupDifferentOfCoffeeTypeTankException
     * @throws LackOfWaterInTankException
     * @throws CupNotEmptyException
     * @throws CannotMakeCremaWithSimpleCoffeeMachine
     * @throws InterruptedException
     * @throws MachineNotPluggedException
     */
    @Test
    public void testMakeCoffeeTypeCupDifferentOfCoffeeTypeTankException() throws BeanTypeDifferentOfCoffeeTypeTankException, CoffeeTypeCupDifferentOfCoffeeTypeTankException, LackOfWaterInTankException, CupNotEmptyException, CannotMakeCremaWithSimpleCoffeeMachine, InterruptedException, MachineNotPluggedException {
        //Empêche la machine d'être hors service
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
        coffeeMachineUnderTest.setRandomGenerator(randomMock);

        coffeeMachineUnderTest.plugToElectricalPlug();
        coffeeMachineUnderTest.addCoffeeInBeanTank(2,CoffeeType.BAHIA);
        coffeeMachineUnderTest.addWaterInTank(3);

        Cup cup = new Cup(1);
        try {
            coffeeMachineUnderTest.makeACoffee(cup, CoffeeType.MOKA);
            fail("Coffee type different from bean tank coffee type");
        }catch (CoffeeTypeCupDifferentOfCoffeeTypeTankException e){};
    }

    /**
     * On teste si le type de café en sortie est le même qu'en entrée
     * @throws BeanTypeDifferentOfCoffeeTypeTankException
     * @throws CoffeeTypeCupDifferentOfCoffeeTypeTankException
     * @throws LackOfWaterInTankException
     * @throws CupNotEmptyException
     * @throws CannotMakeCremaWithSimpleCoffeeMachine
     * @throws InterruptedException
     * @throws MachineNotPluggedException
     */
    @Test
    public void testMakeCoffeeType() throws BeanTypeDifferentOfCoffeeTypeTankException, LackOfWaterInTankException, CupNotEmptyException, CannotMakeCremaWithSimpleCoffeeMachine, InterruptedException, MachineNotPluggedException, CoffeeTypeCupDifferentOfCoffeeTypeTankException {
        //Empêche la machine d'être hors service
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
        coffeeMachineUnderTest.setRandomGenerator(randomMock);

        coffeeMachineUnderTest.plugToElectricalPlug();
        coffeeMachineUnderTest.addCoffeeInBeanTank(2,CoffeeType.BAHIA);
        coffeeMachineUnderTest.addWaterInTank(3);

        Cup cup = new Cup(1);
        CoffeeContainer coffeeContainer= coffeeMachineUnderTest.makeACoffee(cup, CoffeeType.BAHIA);
        assertEquals(coffeeContainer.getCoffeeType(),CoffeeType.BAHIA);
    }

    /**On teste que si le contenant est une Cup, on retourne un CoffeeCup non vide avec la bonne capacité
     */
    @Test
    public void testMakeCoffeeCupReturned() throws BeanTypeDifferentOfCoffeeTypeTankException, CoffeeTypeCupDifferentOfCoffeeTypeTankException, LackOfWaterInTankException, CupNotEmptyException, CannotMakeCremaWithSimpleCoffeeMachine, InterruptedException, MachineNotPluggedException {
        //Empêche la machine d'être hors service
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
        coffeeMachineUnderTest.setRandomGenerator(randomMock);

        coffeeMachineUnderTest.plugToElectricalPlug();
        coffeeMachineUnderTest.addCoffeeInBeanTank(2,CoffeeType.BAHIA);
        coffeeMachineUnderTest.addWaterInTank(3);

        Cup cup = new Cup(1);

        CoffeeCup madeCoffee = (CoffeeCup) coffeeMachineUnderTest.makeACoffee(cup,CoffeeType.BAHIA);
        assertFalse(madeCoffee.isEmpty());
        assertEquals(madeCoffee.getCapacity(),cup.getCapacity());
    }
    /**On teste que si le contenant est une Mug, on retourne un CoffeeMug non vide avec la bonne capacité
     */
    @Test
    public void testMakeCoffeeMugReturned() throws BeanTypeDifferentOfCoffeeTypeTankException, CoffeeTypeCupDifferentOfCoffeeTypeTankException, LackOfWaterInTankException, CupNotEmptyException, CannotMakeCremaWithSimpleCoffeeMachine, InterruptedException, MachineNotPluggedException {
        //Empêche la machine d'être hors service
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
        coffeeMachineUnderTest.setRandomGenerator(randomMock);

        coffeeMachineUnderTest.plugToElectricalPlug();
        coffeeMachineUnderTest.addCoffeeInBeanTank(2,CoffeeType.BAHIA);
        coffeeMachineUnderTest.addWaterInTank(3);

        Mug mug = new Mug(1);

        CoffeeMug madeCoffee = (CoffeeMug) coffeeMachineUnderTest.makeACoffee(mug,CoffeeType.BAHIA);
        assertFalse(madeCoffee.isEmpty());
        assertEquals(madeCoffee.getCapacity(),mug.getCapacity());
    }

    /**
     * On tests que le nombre de café fait pas la machine est incrémenté de 1
     * @throws BeanTypeDifferentOfCoffeeTypeTankException
     * @throws CoffeeTypeCupDifferentOfCoffeeTypeTankException
     * @throws LackOfWaterInTankException
     * @throws CupNotEmptyException
     * @throws CannotMakeCremaWithSimpleCoffeeMachine
     * @throws InterruptedException
     * @throws MachineNotPluggedException
     */
    @Test
    public void testMakeCoffeeNumber() throws BeanTypeDifferentOfCoffeeTypeTankException, CoffeeTypeCupDifferentOfCoffeeTypeTankException, LackOfWaterInTankException, CupNotEmptyException, CannotMakeCremaWithSimpleCoffeeMachine, InterruptedException, MachineNotPluggedException {
        //Empêche la machine d'être hors service
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
        coffeeMachineUnderTest.setRandomGenerator(randomMock);

        coffeeMachineUnderTest.plugToElectricalPlug();
        coffeeMachineUnderTest.addCoffeeInBeanTank(2,CoffeeType.BAHIA);
        coffeeMachineUnderTest.addWaterInTank(3);

        Mug mug = new Mug(1);

        int nbCoffeeMade = coffeeMachineUnderTest.getNbCoffeeMade();
        coffeeMachineUnderTest.makeACoffee(mug,CoffeeType.BAHIA);

        assertEquals(nbCoffeeMade+1,coffeeMachineUnderTest.getNbCoffeeMade());
    }
    @AfterEach
    public void afterTest(){

    }
}
