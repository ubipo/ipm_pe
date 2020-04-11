package net.pfiers.ipm_minor_ex1.util;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SlugUUIDFormatterTest {
    SlugUUIDFormatter converter = new SlugUUIDFormatter();
    private static final Locale LOCALE = Locale.ENGLISH;

    private static String uuidStr = "6a2cf644-524b-4359-b2c4-fc5a0b7b80ac";
    private static UUID uuid = UUID.fromString(uuidStr);
    private static UUID uuidSpecial = UUID.fromString("67f9d86e-403b-48b9-acfa-52b8948fe9bd");
    private static String urlSafeRe = "[a-zA-Z0-9_-]";


    @Test
    void parseUuid() {
        var parsed = converter.parse(uuidStr, LOCALE);
        assertEquals(uuid, parsed);
    }

    @Test
    void parseInvalidSlug() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.parse("not-an-actual-slug", LOCALE);
        });
    }

    @Test
    void printParse() {
        var printed = converter.print(uuid, LOCALE);
        var parsed = converter.parse(printed, LOCALE);
        assertEquals(uuid, parsed);
    }

    @Test
    void printChars() {
        var printed = converter.print(uuid, LOCALE);
        assertEquals("", printed.replaceAll(urlSafeRe, ""));
    }

    @Test
    void printCharsSpecial() {
        var printed = converter.print(uuidSpecial, LOCALE);
        assertEquals("", printed.replaceAll(urlSafeRe, ""));
    }
}
