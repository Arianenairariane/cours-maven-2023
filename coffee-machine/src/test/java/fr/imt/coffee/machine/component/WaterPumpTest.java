package fr.imt.coffee.machine.component;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WaterPumpTest {

    WaterPump waterPumpUnderTest;
    @BeforeAll
    public void beforeClass(){
        waterPumpUnderTest = new WaterPump(400);
    }
    @Test
    public void testPumpWaterTime() throws InterruptedException {
        double waterVolume = 3;

        WaterTank waterTank = new WaterTank(6,1,8);
        double pumpingTime = (waterVolume / waterPumpUnderTest.getPumpingCapacity()) * 1000 * 2;
        assertEquals(pumpingTime,waterPumpUnderTest.pumpWater(waterVolume,waterTank));
    }

}
