package shwendel.reallyawesomecontests.util;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    private static final TimeUnit[] TIME_UNITS = new TimeUnit[] { TimeUnit.DAYS, TimeUnit.HOURS, TimeUnit.MINUTES, TimeUnit.SECONDS };

    public static long timeFormatToMillis(String timeFormat) {
        String[] split = timeFormat.split(" ");

        int number = Integer.parseInt(split[0]);
        String timeType = split[1];

        if(!timeType.endsWith("s")) {
            timeType = timeType + "s";
        }
        TimeUnit timeUnit = TimeUnit.valueOf(timeType.toUpperCase());

        return timeUnit.toMillis(number);
    }

    public static String toTimeFormat(long millis) {

        String time = "";

        for(TimeUnit timeUnit : TIME_UNITS) {
            long timeUnitMillisTime = timeUnit.toMillis(1);
            if(millis >= timeUnitMillisTime) {
                int r = (int) Math.floor(millis / timeUnitMillisTime);
                millis -= r * timeUnitMillisTime;
                time += r + timeUnit.toString().substring(0, 1).toLowerCase();
            }
        }

        return time;
    }

}
