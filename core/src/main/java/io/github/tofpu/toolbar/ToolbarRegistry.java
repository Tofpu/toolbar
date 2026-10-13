package io.github.tofpu.toolbar;

import io.tofpu.toolbar.toolbar.Toolbar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public final class ToolbarRegistry {
    private static final Logger log = LoggerFactory.getLogger(ToolbarRegistry.class);
    private final Map<String, Toolbar> toolbarMap;

    public ToolbarRegistry() {
        this.toolbarMap = new HashMap<>();
    }

    public void register(final Toolbar toolbar) {
        log.debug("Registering {} as {}", toolbar, toolbar.id());
        this.toolbarMap.put(toolbar.id(), toolbar);
    }

    public Toolbar findToolbarBy(final String id) {
        if (id == null) return null;
        return toolbarMap.get(id);
    }
}
