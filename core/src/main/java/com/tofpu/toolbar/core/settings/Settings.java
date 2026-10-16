package com.tofpu.toolbar.core.settings;

public interface Settings {
    static Builder newBuilder() {
        return new Builder();
    }

    boolean verbose();

    class Builder {
        private boolean verbose = false;

        public boolean verbose() {
            return verbose;
        }

        public Builder setVerbose(boolean verbose) {
            this.verbose = verbose;
            return this;
        }

        public Settings build() {
            return new SettingsImpl(verbose);
        }
    }
}
