package cz.wild.routerecord.helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeHelper {

    /**
     * Převede sekundy na hodiny:minuty:sekundy jako textový řetězec
     * @param time
     * @return
     */
    public static String secondToStringTime(long time){
        String hours = fixDisplayOfZero(time / 3600); // /60/60
        String minutes = fixDisplayOfZero(((time % 86400) % 3600) / 60); // /60
        String seconds = fixDisplayOfZero(((time % 86400) % 3600) % 60);
        return hours + ":" + minutes + ":" + seconds;
    }

    /**
     * Opraví zobrazení nul. 
     * @param value
     * @return String value
     */
    private static String fixDisplayOfZero(long value){
        if(value <10) return "0" + value;   // Pokud je jednociferné, přidáme před něj nulu
        return String.valueOf(value);
    }

    /**
     * Převede timestamp na den.měsíc rok jako textový řetězec
     * @param timestamp
     * @return
     */
    public static String timestampToStringDate(long timestamp){
        long millilsTimeStamp = timestamp * 1000;                                                                       // čas převedeme na milisekundy
        Instant instant =  Instant.ofEpochMilli(millilsTimeStamp);                                                      // získáme objekt instant
        ZoneId defaultZoneId = ZoneId.systemDefault();                                                                  // získáme časové pásmo
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, defaultZoneId);                                       // získáme dateTime pomocí instant a časového pásma
        String date = dateTime.format(DateTimeFormatter.ofPattern("d.MMMM yyyy").withLocale(new Locale("cs"))); // naformátujeme datum
        return date;
    }
}
