package com.bytecodes.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {

    private DateUtil(){}

    public static String getTodaySimpleDate() {
        return getSimpleDate(ZonedDateTime.now());
    }

    public static String getSimpleDate(ZonedDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());
        return formatter.format(date);
    }

}
