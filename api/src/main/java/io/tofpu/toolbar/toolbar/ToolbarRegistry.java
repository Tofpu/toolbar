package io.tofpu.toolbar.toolbar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

final class ToolbarRegistry {
    private static final Logger log = LoggerFactory.getLogger(ToolbarRegistry.class);
    private final Map<String, GenericToolbar<?>> toolbarMap;

    public ToolbarRegistry() {
        this.toolbarMap = new HashMap<>();
    }

    public void register(final GenericToolbar<?> toolbar) {
        log.debug("Registering {} as {}", toolbar, toolbar.getIdentifier());
        this.toolbarMap.put(toolbar.getIdentifier(), toolbar);
    }

    public GenericToolbar<?> findToolbarBy(final String identifier) {
        if (identifier == null) return null;
        return toolbarMap.get(identifier);
    }
}
