package com.tofpu.toolbar.core.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.tofpu.toolbar.core.util.ArrayHelper.getOrDefault;
import static com.tofpu.toolbar.core.util.ParseHelper.parseOrDefault;

public class PlatformVersion {
    private final NumberedVersion numberedVersion;

    public PlatformVersion(String version) {
        this.numberedVersion = numberedVersion(version);
    }

    private NumberedVersion numberedVersion(String version) {
        String[] versionArgs = version // 1.19.2-SNAPSHOT
                .split("-")[0] // [1.19.2] (strips -SNAPSHOT, etc)
                .split("\\."); // [1,19,2]
        int major = parseOrDefault(getOrDefault(versionArgs, 0, "0"));
        int minor = parseOrDefault(getOrDefault(versionArgs, 1, "0"));
        int patch = parseOrDefault(getOrDefault(versionArgs, 2, "0"));
        return new NumberedVersion(major, minor, patch);
    }

    public boolean is(Comparison comparison, NumberedVersion numberedVersion) {
        int result = this.numberedVersion.compareTo(numberedVersion);
        return comparison.matches(result);
    }

    public static class NumberedVersion implements Comparable<NumberedVersion> {
        private final int major;
        private final int minor;
        private final int patch;

        public static NumberedVersion ofMajor(int value) {
            return new NumberedVersion(value, 0, 0);
        }

        public NumberedVersion(int major, int minor, int patch) {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
        }

        @Override
        public int compareTo(@NotNull PlatformVersion.NumberedVersion other) {
            if (this.major != other.major) {
                return Integer.compare(this.major, other.major);
            }
            if (this.minor != other.minor) {
                return Integer.compare(this.minor, other.minor);
            }
            return Integer.compare(this.patch, other.patch);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            NumberedVersion that = (NumberedVersion) o;
            return major == that.major && minor == that.minor && patch == that.patch;
        }

        @Override
        public int hashCode() {
            return Objects.hash(major, minor, patch);
        }
    }

    public enum Comparison {
        GREATER_THAN(result -> result > 0),
        GREATER_THAN_OR_EQUAL(result -> result >= 0),
        EQUAL(result -> result == 0),
        LESS_THAN(result -> result < 0),
        LESSER_THAN_OR_EQUAL(result -> result <= 0);

        private final ComparePredicate predicate;

        Comparison(ComparePredicate predicate) {
            this.predicate = predicate;
        }

        public boolean matches(int compareResult) {
            return predicate.matches(compareResult);
        }

        interface ComparePredicate {
            boolean matches(int result);
        }
    }
}
