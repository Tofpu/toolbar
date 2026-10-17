package com.tofpu.toolbar.api.toolbar.tool;

import com.tofpu.toolbar.api.toolbar.ItemSlot;
import com.tofpu.toolbar.api.toolbar.Toolbar;
import com.tofpu.toolbar.api.toolbar.tool.action.ToolAction;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.StringJoiner;

class ToolImpl implements Tool {
    private final String id;
    private final ItemStack item;
    private final ToolAction<? extends Event> action;
    private final ItemSlot slot;

    public ToolImpl(final String id, final ItemStack item, final ToolAction<? extends Event> action, final ItemSlot slot) {
        this.id = id;
        this.item = item;
        this.action = action;
        this.slot = slot;
    }

    @Override
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

    @Override
    public String id() {
        return id;
    }

    @Override
    public ItemStack item() {
        return item;
    }

    @Override
    public ToolAction<?> action() {
        return action;
    }

    @Override
    public ItemSlot slot() {
        return slot;
    }

    @Override
    public Tool at(ItemSlot newSlot) {
        return new ToolImpl(id, item, action, newSlot);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ToolImpl.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("item=" + item)
                .add("action=" + action)
                .toString();
    }
}
