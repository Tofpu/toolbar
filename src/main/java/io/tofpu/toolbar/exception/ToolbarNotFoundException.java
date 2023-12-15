package io.tofpu.toolbar.exception;

public final class ToolbarNotFoundException extends Exception {
    public ToolbarNotFoundException(final String identifier) {
        super("Umbrella not found: " + identifier);
    }
}
