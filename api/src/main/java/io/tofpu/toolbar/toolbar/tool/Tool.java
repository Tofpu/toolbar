package io.tofpu.toolbar.toolbar.tool;

import io.tofpu.toolbar.toolbar.ItemSlot;
import io.tofpu.toolbar.toolbar.GenericToolbar;
import io.tofpu.toolbar.toolbar.tool.action.ToolAction;
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

    public String getItemIdentifier() {
        return itemIdentifier;
    }

    public ItemStack getItem() {
        return item;
    }

    public ToolAction<?> getAction() {
        return action;
    }

    public void trigger(final GenericToolbar<?> owner, final Event event) {
        // if the item action is null, don't do anything
        if (action == null) {
            return;
        }

        if (action.isCompatible(event)) {
            action.call(owner, event);
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
