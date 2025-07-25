package utils;

import java.time.LocalDate;

public class DateUtils {

    public static String getDayofMonth(int plusDay){
        LocalDate date = LocalDate.now().plusDays(plusDay);
        return String.valueOf(date.getDayOfMonth());
    }
}
