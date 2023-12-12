package fr.imt.coffee.machine.component;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SteamPipeTest {
    SteamPipe steamPipeUnderTest;
    @BeforeAll
    public void beforeClass(){
        steamPipeUnderTest = new SteamPipe();
    }
    @Test
    public void testSetOn(){
        steamPipeUnderTest.setOn();
        assertTrue(steamPipeUnderTest.isOn());
    }
    @Test
    public void testSetOff(){
        steamPipeUnderTest.setOff();
        assertFalse(steamPipeUnderTest.isOn());
    }
}
