package net.stevexmh.realtimemod.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RealTime {
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillis = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillis,TimeUnit.MILLISECONDS);
    }

    public static long getRealDateTime() {
        return new Date().getTime() / 86400000 * 86400000;
    }

    public static long getRealMinecraftDateTime() {
        return getRealDateTime() / 3600;
    }

    public static long getRealTime() {
        return new Date().getTime();
    }

    public static long getRealMinecraftTime() {
        return getRealTime() / 3600;
    }
}
