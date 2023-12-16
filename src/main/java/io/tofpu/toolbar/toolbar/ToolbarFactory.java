package io.tofpu.toolbar.toolbar;

import io.tofpu.toolbar.toolbar.tool.Tool;

import java.util.ArrayList;
import java.util.Collection;

public final class ToolbarFactory {

    private ToolbarFactory() {
    }

    public static GenericToolbar create(final String identifier) {
        return create(identifier, new ArrayList<>());
    }

    public static GenericToolbar create(final String identifier,
                                        final Collection<Tool> tools) {
        return new GenericToolbar(identifier, tools);
    }
}
