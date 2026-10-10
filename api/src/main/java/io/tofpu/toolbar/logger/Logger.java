package io.tofpu.toolbar.logger;

public class Logger {
    private final java.util.logging.Logger delegate;
    private final boolean verbose;

    public Logger(java.util.logging.Logger delegate, boolean verbose) {
        this.delegate = delegate;
        this.verbose = verbose;
    }

    public void log(String message) {
        delegate.info(message);
    }

    public void debug(String message) {
        if (!verbose) {
            return;
        }
        delegate.info("DEBUG: " + message);
    }
}
