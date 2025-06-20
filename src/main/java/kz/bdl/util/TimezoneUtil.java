package kz.bdl.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimezoneUtil {

    private static final String SOURCE_TIMEZONE = "GMT";
    
    /**
     * Converts GMT time to local time
     * @param gmtDateTime The GMT time to convert
     * @return Local time in the configured timezone
     */
    public LocalDateTime convertGmtToLocal(LocalDateTime gmtDateTime) {
        if (gmtDateTime == null) {
            return null;
        }
        
        ZonedDateTime gmtTime = gmtDateTime.atZone(ZoneId.of(SOURCE_TIMEZONE));
        return gmtTime.toLocalDateTime();
    }
} 