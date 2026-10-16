package com.tofpu.toolbar.core.settings;

public class SettingsImpl implements Settings {
    private final boolean verbose;

    public SettingsImpl(boolean verbose) {
        this.verbose = verbose;
    }

    @Override
    public boolean verbose() {
        return verbose;
    }
}
