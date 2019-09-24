package stu.homework.WorldClock;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

// 世界时钟系统，要求phone clock必须第一个插入
public class WorldClockSystem {
    private Map<String, Clock> cityClocks = new HashMap<String, Clock>();

    public void addClock(Clock clock) {
        cityClocks.put(clock.getLocation(), clock);
    }

    public Clock getClock(String location) {
        return cityClocks.get(location);
    }

    void updateClocks(LocalTime UTCTime) {

        boolean isPhone = true;
        for (Clock clock : cityClocks.values()) {

            if (!isPhone) {
                clock.setLocalTime(UTCTime.minusHours(clock.getUTCOffSet()));
            }
            else {
                isPhone = false;
            }

        }
    }
}
