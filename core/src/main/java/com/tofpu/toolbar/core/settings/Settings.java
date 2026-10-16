package com.tofpu.toolbar.core.settings;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public interface Settings {
    Settings DEFAULT = Settings.newBuilder().build();

    static Builder newBuilder() {
        return new Builder();
    }

    boolean verbose();
    String[] packages();

    class Builder {
        private boolean verbose = false;
        private final Set<String> packages = new HashSet<>(1);

        public boolean verbose() {
            return verbose;
        }

        public Builder setVerbose(boolean verbose) {
            this.verbose = verbose;
            return this;
        }

        public Builder setPackages(Collection<String> packages) {
            this.packages.clear();
            this.packages.addAll(packages);
            return this;
        }

        public Builder addPackage(String packagePath) {
            this.packages.add(packagePath);
            return this;
        }

        public Settings build() {
            String[] packagesArray = packages.toArray(new String[0]);
            return new SettingsImpl(verbose, packagesArray);
        }
    }
}