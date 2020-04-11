package net.pfiers.ipm_minor_ex1.util;

import com.joestelmach.natty.Parser;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.util.TimeZone;

public class StringArrToInstantConverter implements Converter<String[], Instant> {
    public static TimeZone TIME_ZONE = TimeZone.getTimeZone("CET");

    @Override
    public Instant convert(String[] source) {
        var parser = new Parser(TIME_ZONE);
        var instantParsed = parser.parse(String.join(" ", source));
        return instantParsed.get(0).getDates().get(0).toInstant();
    }
}
