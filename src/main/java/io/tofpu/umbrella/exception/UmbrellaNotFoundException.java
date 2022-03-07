package io.tofpu.umbrella.exception;

public final class UmbrellaNotFoundException extends Exception {
    public UmbrellaNotFoundException(final String identifier) {
        super("Umbrella not found: " + identifier);
    }
}
