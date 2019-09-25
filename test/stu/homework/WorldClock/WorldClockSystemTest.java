package stu.homework.WorldClock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class WorldClockSystemTest {
    static Clock[] clocks = new Clock[5];
    static WorldClockSystem worldClockSystem;


    @Test
    @BeforeAll
    static void init() {
        clocks[0] = new PhoneClock("Beijing", -8);
        clocks[1] = new Clock("London", 0);
        clocks[2] = new Clock("Moscow", -4);
        clocks[3] = new Clock("Sydney", -10);
        clocks[4] = new Clock("New York", 5);
        worldClockSystem = new WorldClockSystem();
        for(int i = 0; i < 5; i++) {
            worldClockSystem.addClock(clocks[i]);
        }
    }

    @Test
    @DisplayName("Test ClockList in Class WorldClockSystem")
    void testClockListUse() {
        clocks[0].setLocalTime(LocalTime.of(1, 10));
        clocks[1].setLocalTime(LocalTime.of(2, 20));
        clocks[2].setLocalTime(LocalTime.of(3, 30));
        Clock[] testClocks = new Clock[3];
        testClocks[0] = worldClockSystem.getClock("Beijing");
        testClocks[1] = worldClockSystem.getClock("London");
        testClocks[2] = worldClockSystem.getClock("Moscow");
        assertAll(
                ()->assertEquals("Beijing: 01:10", testClocks[0].getLocation() + ": " + testClocks[0].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("London: 02:20", testClocks[1].getLocation() + ": " + testClocks[1].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("Moscow: 03:30", testClocks[2].getLocation() + ": " + testClocks[2].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
        );
    }

    @Test
    @DisplayName("Test the PhoneClock's calibration function")
    void testPhoneClockCalibration() {
        ((PhoneClock)clocks[0]).setWorldClockSystem(worldClockSystem);
        clocks[1].setLocalTime(LocalTime.of(2, 30));
        clocks[2].setLocalTime(LocalTime.of(3, 30));
        clocks[3].setLocalTime(LocalTime.of(4, 30));
        clocks[4].setLocalTime(LocalTime.of(5, 30));
        clocks[0].setLocalTime(LocalTime.of(12, 30));
        assertAll(
                ()->assertEquals("London: 04:30", clocks[1].getLocation() + ": " + clocks[1].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("Moscow: 08:30", clocks[2].getLocation() + ": " + clocks[2].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("Sydney: 14:30", clocks[3].getLocation() + ": " + clocks[3].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("New York: 23:30", clocks[4].getLocation() + ": " + clocks[4].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
        );
    }

}
