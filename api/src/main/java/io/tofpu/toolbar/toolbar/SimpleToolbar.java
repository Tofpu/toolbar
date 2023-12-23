package io.tofpu.toolbar.toolbar;

import io.tofpu.toolbar.toolbar.tool.Tool;

import java.util.Collection;

public class SimpleToolbar extends GenericToolbar<Tool> {
    public SimpleToolbar(String identifier) {
        super(identifier);
    }

    public SimpleToolbar(String identifier, Collection<? extends Tool> tools) {
        super(identifier, tools);
    }
}
