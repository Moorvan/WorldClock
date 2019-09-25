package stu.homework.WorldClock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;


class ClockTest {
    static Clock[] clocks = new Clock[5];

    @Test
    @BeforeAll
    static void init() {
        clocks[0] = new PhoneClock("Beijing", -8);
        clocks[1] = new Clock("London", 0);
        clocks[2] = new Clock("Moscow", -4);
        clocks[3] = new Clock("Sydney", -10);
        clocks[4] = new Clock("New York", 5);
    }

    @Test
    @DisplayName("Set and get the Clock time")
    void testClockSetGet() {
        clocks[0].setLocalTime(LocalTime.of(1, 10));
        clocks[1].setLocalTime(LocalTime.of(3, 20));
        clocks[2].setLocalTime(LocalTime.of(4, 10));
        assertAll(
                ()->assertEquals("Beijing(offset:-8): 01:10", clocks[0].getLocation() + "(offset:" + clocks[0].getUTCOffSet() + "): " + clocks[0].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("London(offset:0): 03:20", clocks[1].getLocation() + "(offset:" + clocks[1].getUTCOffSet() + "): " + clocks[1].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))),
                ()->assertEquals("Moscow(offset:-4): 04:10", clocks[2].getLocation() + "(offset:" + clocks[2].getUTCOffSet() + "): " + clocks[2].getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                );
    }


}