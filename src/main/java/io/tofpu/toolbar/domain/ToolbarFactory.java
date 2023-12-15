package io.tofpu.toolbar.domain;

import io.tofpu.toolbar.domain.Toolbar;
import io.tofpu.toolbar.domain.item.Tool;
import io.tofpu.toolbar.domain.ToolbarRegistry;

import java.util.ArrayList;
import java.util.Collection;

public final class ToolbarFactory {
    private final ToolbarRegistry toolbarRegistry;

    public ToolbarFactory(final ToolbarRegistry toolbarRegistry) {
        this.toolbarRegistry = toolbarRegistry;
    }

    public Toolbar create(final String identifier) {
        return create(identifier, new ArrayList<>());
    }

    public Toolbar create(final String identifier,
                          final Collection<Tool> tools) {
        final Toolbar toolbar = new Toolbar(toolbarRegistry, identifier, tools);
        toolbarRegistry.register(toolbar);

        return toolbar;
    }
}
