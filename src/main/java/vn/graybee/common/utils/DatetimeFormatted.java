package vn.graybee.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatetimeFormatted {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static String formatted_datetime() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

}
