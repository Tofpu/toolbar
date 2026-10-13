package io.github.tofpu.util;

public class ParseHelper {
    private ParseHelper() {
        /* This utility class should not be instantiated */
    }

    public static int parseOrDefault(String val) {
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
