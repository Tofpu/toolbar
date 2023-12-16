package io.tofpu.toolbar.toolbar;

import io.tofpu.toolbar.toolbar.tool.Tool;

import java.util.ArrayList;
import java.util.Collection;

public final class ToolbarFactory {

    private ToolbarFactory() {
    }

    public static Toolbar create(final String identifier) {
        return create(identifier, new ArrayList<>());
    }

    public static Toolbar create(final String identifier,
                                 final Collection<Tool> tools) {
        return new Toolbar(identifier, tools);
    }
}
