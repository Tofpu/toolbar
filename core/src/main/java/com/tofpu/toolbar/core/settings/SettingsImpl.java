package com.tofpu.toolbar.core.settings;

public class SettingsImpl implements Settings {
    private final boolean verbose;
    private final String[] packages;

    public SettingsImpl(boolean verbose, String[] packages) {
        this.verbose = verbose;
        this.packages = packages;
    }

    @Override
    public boolean verbose() {
        return verbose;
    }

    @Override
    public String[] packages() {
        return packages;
    }
}