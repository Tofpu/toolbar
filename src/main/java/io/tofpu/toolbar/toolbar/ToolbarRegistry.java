package io.tofpu.toolbar.toolbar;

import java.util.HashMap;
import java.util.Map;

final class ToolbarRegistry {
    private final Map<String, Toolbar> toolbarMap;

    public ToolbarRegistry() {
        this.toolbarMap = new HashMap<>();
    }

    public void register(final Toolbar toolbar) {
        this.toolbarMap.put(toolbar.getIdentifier(), toolbar);
    }

    public Toolbar findToolbarBy(final String identifier) {
        if (identifier == null) return null;
        return toolbarMap.get(identifier);
    }
}
