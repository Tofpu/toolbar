package io.tofpu.toolbar.toolbar;

import io.tofpu.toolbar.toolbar.tool.Tool;

public class ToolWithSlot<T extends Tool> {
    private final T tool;
    private final ItemSlot slot;

    public ToolWithSlot(T tool, ItemSlot slot) {
        this.tool = tool;
        this.slot = slot;
    }

    public ToolWithSlot(ToolWithSlot<T> clone, ItemSlot slot) {
        this.tool = clone.tool;
        this.slot = slot;
    }

    public T tool() {
        return tool;
    }

    public ItemSlot slot() {
        return slot;
    }
}
