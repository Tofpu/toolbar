package io.tofpu.toolbar.toolbar;

import io.tofpu.toolbar.toolbar.tool.Tool;

public class SimpleToolbar extends GenericToolbar<Tool> {
    public SimpleToolbar(String identifier) {
        super(identifier);
    }

    public SimpleToolbar(String identifier, Tool... tools) {
        super(identifier, tools);
    }

    public SimpleToolbar(String identifier, ToolWithSlot<Tool>... toolWithSlots) {
        super(identifier, toolWithSlots);
    }
}
