package fr.imt.coffee.machine.component;

import fr.imt.coffee.cupboard.coffee.type.CoffeeType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CoffeeGrinderTest {
    CoffeeGrinder coffeeGrinderUnderTest;
    BeanTank beanTank;
    @BeforeAll
    public void beforeClass(){
        coffeeGrinderUnderTest = new CoffeeGrinder(2000);
        beanTank = new BeanTank(3,0,7, CoffeeType.BAHIA);
    }

    /**
     * On teste si le temps de mouture est bien celui de la machine
     * @throws InterruptedException
     */
    @Test
    public void testGrindCoffeeTime() throws InterruptedException {
        double grindingTime = coffeeGrinderUnderTest.grindCoffee(beanTank);
        assertEquals(grindingTime,2000);
    }

    /**
     * On teste si le fait de moudre le café vide le réservoir de grains
     * @throws InterruptedException
     */
    @Test
    public void testGrindCoffeeEmptiesTank() throws InterruptedException {
        assertNotEquals(beanTank.getActualVolume(),beanTank.getMinVolume());
        coffeeGrinderUnderTest.grindCoffee(beanTank);
        assertEquals(beanTank.getActualVolume(),beanTank.getMinVolume());
    }
}
