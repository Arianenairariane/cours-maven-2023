package fr.imt.coffee.machine.component;

import fr.imt.coffee.cupboard.coffee.type.CoffeeType;
import fr.imt.coffee.machine.exception.BeanTypeDifferentOfCoffeeTypeTankException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BeanTankTest {
    BeanTank beanTankUnderTest;
    @BeforeEach
    public void beforeTest(){
        beanTankUnderTest = new BeanTank(5,1,10, CoffeeType.BAHIA);
    }

    /**
     On teste si le volume augmente bien, avec le bon type de grain
     */
    @Test
    public void testIncreaseCoffeeVolumeInTank() throws BeanTypeDifferentOfCoffeeTypeTankException {
        beanTankUnderTest.increaseCoffeeVolumeInTank(4,CoffeeType.BAHIA);
        assertEquals(9,beanTankUnderTest.getActualVolume());
        assertEquals(CoffeeType.BAHIA,beanTankUnderTest.getBeanCoffeeType());
    }
    /**
     On teste si le volume augmente bien dans le cas où le réservoir est vide et où on change le type de grain
     */
    @Test
    public void testIncreaseCoffeeVolumeInTankChangeBean() throws BeanTypeDifferentOfCoffeeTypeTankException {
        beanTankUnderTest = new BeanTank(1,1,8,CoffeeType.ARABICA);
        beanTankUnderTest.increaseCoffeeVolumeInTank(4,CoffeeType.BAHIA);
        assertEquals(5,beanTankUnderTest.getActualVolume());
        assertEquals(CoffeeType.BAHIA,beanTankUnderTest.getBeanCoffeeType());
    }
    /**
     * On teste le cas où a réduire est négatif
     */
    @Test
    public void testDecreaseVolumeInTankNegativeInput(){
        try{
            beanTankUnderTest.decreaseVolumeInTank(-3);
            fail("Le volume a réduire est négatif, ce ne doit pas être possible");
        }catch (IllegalArgumentException e){}
    }

    /**
     * On teste la cas où le volume a réduire est supérieur au (volume initial - volume minimal)
     */
    @Test
    public void testDecreaseVolumeInTankNotEnough(){
        try{
            beanTankUnderTest.decreaseVolumeInTank(7);
            fail("Le volume à réduire est supérieur à ce qui peut être réduit");
        }catch (IllegalArgumentException e){}
    }
    /**
     On teste si une exception est levée si le type de grain ne correspond pas au grain déjà présent
     */
    @Test
    public void testIncreaseCoffeeVolumeInTankFailureWrongType(){
        try{
            beanTankUnderTest.increaseCoffeeVolumeInTank(4,CoffeeType.ARABICA);
            fail("Ajout d'un grain différent de celui déjà présent");
        }catch (BeanTypeDifferentOfCoffeeTypeTankException e){
        }
    }
    @Test
    public void testEmptyTank(){
        assertNotEquals(beanTankUnderTest.getActualVolume(),beanTankUnderTest.getMinVolume());
        beanTankUnderTest.emptyTank();
        assertEquals(beanTankUnderTest.getActualVolume(),beanTankUnderTest.getMinVolume());
    }
}
