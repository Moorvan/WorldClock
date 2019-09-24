package stu.homework.WorldClock;

import java.time.LocalTime;

public class Clock {
    protected String location;
    protected LocalTime localTime;
    protected int UTCOffSet;

    public Clock(String location, int UTCOffSet) {
        this.location = location;
        this.UTCOffSet = UTCOffSet;
    }

    public String getLocation() {
        return this.location;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public int getUTCOffSet() {
        return UTCOffSet;
    }
}
