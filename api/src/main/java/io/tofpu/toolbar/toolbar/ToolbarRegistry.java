package io.tofpu.toolbar.toolbar;

import java.util.HashMap;
import java.util.Map;

final class ToolbarRegistry {
    private final Map<String, GenericToolbar<?>> toolbarMap;

    public ToolbarRegistry() {
        this.toolbarMap = new HashMap<>();
    }

    public void register(final GenericToolbar<?> toolbar) {
        System.out.println(String.format("Registering %s as %s", toolbar, toolbar.getIdentifier()));
        this.toolbarMap.put(toolbar.getIdentifier(), toolbar);
    }

    public GenericToolbar<?> findToolbarBy(final String identifier) {
        if (identifier == null) return null;
        return toolbarMap.get(identifier);
    }
}
