package stu.homework.WorldClock;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class PhoneClock extends Clock{
    private WorldClockSystem worldClockSystem;

    public PhoneClock(String location, int UTCOffSet) {
        super(location, UTCOffSet);
    }

    public void setWorldClockSystem(WorldClockSystem worldClockSystem) {
        this.worldClockSystem = worldClockSystem;
    }


    public void setLocalTime(LocalTime localTime) {
        super.localTime = localTime;
        if(worldClockSystem != null)
            worldClockSystem.updateClocks(localTime.minusHours(this.getUTCOffSet()));
    }

}
