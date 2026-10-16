package com.tofpu.toolbar.api.toolbar.tool;

import com.tofpu.toolbar.api.toolbar.Toolbar;
import com.tofpu.toolbar.api.toolbar.tool.action.ToolAction;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.StringJoiner;

public class Tool {
    private final String itemIdentifier;
    private final ItemStack item;

    private final ToolAction<? extends Event> action;

    public Tool(final String id, final ItemStack item, final ToolAction<? extends Event> action) {
        this.itemIdentifier = id;
        this.item = item;
        this.action = action;
    }

    public Tool(final String id, final ItemStack item) {
        this(id, item, ToolAction.nothing());
    }

    public String id() {
        return itemIdentifier;
    }

    public ItemStack item() {
        return item;
    }

    public ToolAction<?> action() {
        return action;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", Tool.class.getSimpleName() + "[", "]")
                .add("itemIdentifier='" + itemIdentifier + "'")
                .add("item=" + item)
                .add("action=" + action)
                .toString();
    }
}
