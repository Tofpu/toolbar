package io.tofpu.toolbar.toolbar;

import io.tofpu.toolbar.toolbar.item.Tool;

import java.util.ArrayList;
import java.util.Collection;

public final class ToolbarFactory {
    public Toolbar create(final String identifier) {
        return create(identifier, new ArrayList<>());
    }

    public Toolbar create(final String identifier,
                          final Collection<Tool> tools) {
        return new Toolbar(identifier, tools);
    }
}
