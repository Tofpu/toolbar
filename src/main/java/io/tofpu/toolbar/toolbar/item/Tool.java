package io.tofpu.toolbar.toolbar.item;

import io.tofpu.toolbar.toolbar.Toolbar;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Tool {
    private final String itemIdentifier;
    private final ItemStack item;
    private final int index;

    private final ToolAction action;

    public Tool(final String id, final ItemStack item, final int index, final ToolAction action) {
        this.itemIdentifier = id;
        this.item = item;
        this.index = index;
        this.action = action;
    }

    public Tool(final String id, final ItemStack item, final ToolAction action) {
        this(id, item, -1, action);
    }

    public String getItemIdentifier() {
        return itemIdentifier;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getInventoryIndex() {
        return this.index;
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
}
