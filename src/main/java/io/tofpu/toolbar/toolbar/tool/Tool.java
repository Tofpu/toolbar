package io.tofpu.toolbar.toolbar.tool;

import io.tofpu.toolbar.toolbar.ItemSlot;
import io.tofpu.toolbar.toolbar.Toolbar;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.StringJoiner;

public class Tool {
    private final String itemIdentifier;
    private final ItemStack item;
    private final ItemSlot slot;

    private final ToolAction action;

    public Tool(final String id, final ItemStack item, final ItemSlot slot, final ToolAction action) {
        this.itemIdentifier = id;
        this.item = item;
        this.slot = slot;
        this.action = action;
    }

    public Tool(final String id, final ItemStack item, final ToolAction action) {
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

    public ToolAction getAction() {
        return action;
    }

    public void trigger(final Toolbar toolbar, final PlayerInteractEvent event) {
        // if the item action is null, don't do anything
        if (action == null) {
            return;
        }
        action.trigger(toolbar, event);
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
