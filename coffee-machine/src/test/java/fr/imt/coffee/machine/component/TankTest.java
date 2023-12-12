package fr.imt.coffee.machine.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TankTest {
    Tank tank;
    @BeforeEach
    public void setTank(){
        tank = new Tank(6,0,9);
    }

    /**
     * Cas normal : le volume final = volume initial - volume à réduire
     */
    @Test
    public void testDecreaseVolumeInTank(){
        tank.decreaseVolumeInTank(2);
        double finalVolume = 6-2;
        assertEquals(finalVolume,tank.getActualVolume());
    }
    /**
     * Cas où le volume a réduire est négatif
     */
    @Test
    public void testDecreaseVolumeInTankNegativeInput(){
        try{
            tank.decreaseVolumeInTank(-3);
            fail("Le volume a réduire est négatif, ce ne doit pas être possible");
        }catch (IllegalArgumentException e){}
    }

    /**
     * Cas où le volume a réduire est supérieur au (volume initial - volume minimal)
     */
    @Test
    public void testDecreaseVolumeInTankNotEnough(){
        try{
            tank.decreaseVolumeInTank(7);
            fail("Le volume à réduire est supérieur à ce qui peut être réduit");
        }catch (IllegalArgumentException e){}
    }

    /**
     * Cas normal : le volume final = volume initial - volume à réduire
     */
    @Test
    public void testIncreaseVolumeInTank(){
        tank.increaseVolumeInTank(2);
        double finalVolume = 6+2;
        assertEquals(finalVolume,tank.getActualVolume());
    }
    /**
     * Cas où le volume a réduire est négatif
     */
    @Test
    public void testIncreaseVolumeInTankNegativeInput(){
        try{
            tank.increaseVolumeInTank(-3);
            fail("Le volume à ajouter est négatif, ce ne doit pas être possible");
        }catch (IllegalArgumentException e){}
    }

    /**
     * Cas où le volume a réduire est supérieur au (volume initial - volume minimal)
     */
    @Test
    public void testIncreaseVolumeInTankNotEnough(){
        try{
            tank.increaseVolumeInTank(7);
            fail("Le volume a réduire est supérieur à ce qui peut être réduit");
        }catch (IllegalArgumentException e){}
    }
}
