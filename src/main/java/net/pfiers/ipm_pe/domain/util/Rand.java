package net.pfiers.ipm_pe.domain.util;

import java.util.List;
import java.util.Random;

public abstract class Rand {
    private static Random random;

    public static Random getRandom() {
        if (random == null)
            random = new Random();

        return random;
    }

    public static <T> T pick(List<T> list) {
        return list.get(getRandom().nextInt(list.size()));
    }
}
