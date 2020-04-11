package net.pfiers.ipm_minor_ex1.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StringArrToInstantConverterTest {
    private static StringArrToInstantConverter stringArrToInstantConverter = new StringArrToInstantConverter();

    private static final ZonedDateTime tenthOfMarch = ZonedDateTime.of(
            LocalDate.of(2020, 3, 10),
            LocalTime.NOON,
            StringArrToInstantConverter.TIME_ZONE.toZoneId()
    );

    @Test
    void convertLong() {
        assertEquals(
                tenthOfMarch.toInstant(),
                stringArrToInstantConverter.convert(new String[]{"Tenth of march 2020", "12:00"})
        );
    }

    @Test
    void convertShort() {
        assertEquals(
                tenthOfMarch.toInstant(),
                stringArrToInstantConverter.convert(new String[]{"03/10/20", "12:00:00"})
        );
    }
}