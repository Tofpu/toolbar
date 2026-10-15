package com.tofpu.toolbar.core.util;

public class ArrayHelper {
    private ArrayHelper() {
        /* This utility class should not be instantiated */
    }

    public static <T> T getOrDefault(T[] array, int index, T fallback) {
        if (array.length <= index) {
            return fallback;
        }
        return array[index];
    }
}
