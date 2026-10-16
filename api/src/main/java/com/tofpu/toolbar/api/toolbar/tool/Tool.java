package com.tofpu.toolbar.api.toolbar.tool;

import com.tofpu.toolbar.api.toolbar.ItemSlot;
import com.tofpu.toolbar.api.toolbar.Toolbar;
import com.tofpu.toolbar.api.toolbar.tool.action.ToolAction;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.StringJoiner;

public class Tool {
    private final String id;
    private final ItemStack item;
    private final ToolAction<? extends Event> action;
    private final ItemSlot slot;

    public Tool(final String id, final ItemStack item, final ToolAction<? extends Event> action, final ItemSlot slot) {
        this.id = id;
        this.item = item;
        this.action = action;
        this.slot = slot;
    }

    public Tool(final String id, final ItemStack item, final ToolAction<? extends Event> action) {
        this(id, item, action, ItemSlot.undefined());
    }

    public Tool(final String id, final ItemStack item, final ItemSlot slot) {
        this(id, item, ToolAction.nothing(), slot);
    }

    public Tool(final String id, final ItemStack item) {
        this(id, item, ToolAction.nothing(), ItemSlot.undefined());
    }

    public boolean trigger(final Toolbar owner, final Event event) {
        // if the item action is null, don't do anything
        if (action == null) {
            return false;
        }

        if (action.isCompatible(event)) {
            action.call(owner, event);
            return true;
        } else {
            return false;
        }
    }

    public Tool at(ItemSlot newSlot) {
        return new Tool(id, item, action, newSlot);
    }

    public String id() {
        return id;
    }

    public ItemStack item() {
        return item;
    }

    public ToolAction<?> action() {
        return action;
    }

    public ItemSlot slot() {
        return slot;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tool.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("item=" + item)
                .add("action=" + action)
                .toString();
    }
}
