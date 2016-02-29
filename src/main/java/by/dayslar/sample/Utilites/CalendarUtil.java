package by.dayslar.sample.Utilites;

import java.time.LocalDate;

public class CalendarUtil {

    private static LocalDate oldDate;
    private static LocalDate newDate;

    //возвращает дату в милисикундах от 1970.1.1 до значения даты в oldDate
    public static long getOldDate(){
        return oldDate.toEpochDay() * 3600 * 24 * 1000;
    }

    //возвращает дату в милисикундах от 1970.1.1 до значения даты в newDate
    public static long getNewDate(){
        return newDate.toEpochDay() * 3600 * 24 * 1000 + 86400000;
    }

    //устанавливает значение oldDate
    public static void setOldDate(LocalDate oldDate){
        CalendarUtil.oldDate = oldDate;
    }

    //устанавливает значение newDate
    public static void setNewDate(LocalDate newDate) {
        CalendarUtil.newDate = newDate;
    }

}
