package net.pfiers.ipm_pe.util;

import org.springframework.format.Formatter;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Locale;
import java.util.UUID;

public class SlugUUIDFormatter implements Formatter<UUID> {
    private static Base64.Encoder encoder = Base64.getUrlEncoder();
    private static Base64.Decoder decoder = Base64.getUrlDecoder();


    @Override
    public UUID parse(String slug, Locale locale) {
        return parse(slug);
    }

    @Override
    public String print(UUID uuid, Locale locale) {
        return print(uuid);
    }


    public static UUID parse(String slug) {
        UUID uuid = null;
        try {
            uuid = UUID.fromString(slug);
        } catch (IllegalArgumentException ex) {
            // Not a UUID, probably a slug
            uuid = fromBytes(decoder.decode(slug));
        }
        return uuid;
    }

    public static String print(UUID uuid) {
        return encoder.encodeToString(fromBytes(uuid)).substring(0, 22);
    }

    public static UUID fromBytes(byte[] bytes) {
        if (bytes.length != 16)
            throw new IllegalArgumentException("<bytes> array must be length 16");
        var bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        return new UUID(high, low);
    }

    public static byte[] fromBytes(UUID uuid) {
        var bb = ByteBuffer.allocate(16);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}
