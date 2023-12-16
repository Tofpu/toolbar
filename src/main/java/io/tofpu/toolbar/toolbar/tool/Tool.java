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
    private final ItemSlot slot;

    private final ToolAction<? extends Event> action;

    public Tool(final String id, final ItemStack item, final ItemSlot slot, final ToolAction<? extends Event> action) {
        this.itemIdentifier = id;
        this.item = item;
        this.slot = slot;
        this.action = action;
    }

    public Tool(final String id, final ItemStack item, final ToolAction<?> action) {
        this(id, item, ItemSlot.undefined(), action);
    }

    public Tool(final String id, final ItemStack item, final ItemSlot slot) {
        this(id, item, slot, ToolAction.empty());
    }

    public Tool(final String id, final ItemStack item) {
        this(id, item, ItemSlot.undefined());
    }

    public String getItemIdentifier() {
        return itemIdentifier;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemSlot getSlot() {
        return this.slot;
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
                .add("index=" + slot)
                .add("action=" + action)
                .toString();
    }
}
