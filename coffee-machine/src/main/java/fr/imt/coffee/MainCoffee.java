package fr.imt.coffee;

import fr.imt.coffee.machine.CoffeeMachine;
import fr.imt.coffee.machine.exception.*;
import fr.imt.coffee.cupboard.FabricCupboardContainer;
import fr.imt.coffee.cupboard.coffee.type.CoffeeType;
import fr.imt.coffee.cupboard.container.Container;
import fr.imt.coffee.cupboard.container.Cup;
import fr.imt.coffee.cupboard.container.Mug;
import fr.imt.coffee.cupboard.exception.CupNotEmptyException;
import fr.imt.coffee.cupboard.exception.ExceptionContainerCreation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MainCoffee {

    //Instanciation du logger à partir d'un LogManager.
    //On vient préciser le nom de la classe qui va produire des logs
    //Le logger va permettre de logger suivant différents niveaux :
    //INFO, WARNING, DEBUG, ERROR...
    public static final Logger logger = LogManager.getLogger(MainCoffee.class);

    public static void main(String[] args) throws BeanTypeDifferentOfCoffeeTypeTankException {

        FabricCupboardContainer fabricCupboardContainer = FabricCupboardContainer.getFabricContainerInstance();
        CoffeeMachine coffeeMachine =
                new CoffeeMachine(0.20, 3,
                0.20, 3,600);

        coffeeMachine.plugToElectricalPlug();
        coffeeMachine.addWaterInTank(2);
        coffeeMachine.addCoffeeInBeanTank(1.5,fr.imt.coffee.cupboard.coffee.type.CoffeeType.ROBUSTA);

        logger.info(coffeeMachine.toString());

        Cup cup = null;
        Container coffeeCup = null;

        try {
            cup = (Cup) fabricCupboardContainer.getContainer("cup",0.15);
            cup.setEmpty(true);
            coffeeCup = coffeeMachine.makeACoffee(cup, CoffeeType.ROBUSTA);
            logger.info(coffeeCup.toString());
        } catch (InterruptedException e) {
            //On se doit d'éviter de printer la stackTrace directement dans un system.out.[..].
            //Risque potentiel d'exploitation malveillante de la trace via une remontée directe dans la console/interface web.
            //e.printStackTrace();
            //on préférera logger l'exception levée avec le logger
            logger.throwing(e);
        } catch (CupNotEmptyException e) {
            logger.error(e.getMessage());
            cup.setEmpty(true);
        } catch (LackOfWaterInTankException e) {
            logger.error(e.getMessage());
            coffeeMachine.addWaterInTank(2);
        } catch (ExceptionContainerCreation e) {
            logger.error(e.getMessage());
            coffeeCup = new Mug(0.35);
        } catch (MachineNotPluggedException e) {
            logger.error(e.getMessage());
            coffeeMachine.plugToElectricalPlug();
        } catch (CoffeeTypeCupDifferentOfCoffeeTypeTankException e) {
            logger.error(e.getMessage());
        } catch (CannotMakeCremaWithSimpleCoffeeMachine e) {
            logger.error(e.getMessage());
        }

    }
}
