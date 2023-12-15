package io.tofpu.toolbar.domain.item;

import io.tofpu.toolbar.domain.Toolbar;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Tool {
    private final Toolbar owner;
    private final String itemIdentifier;
    private final ItemStack item;
    private final int index;

    private final AbstractToolAction itemAction;

    public Tool(final Toolbar owner, final String itemIdentifier, final ItemStack item, final int index, final AbstractToolAction itemAction) {
        this.owner = owner;
        this.itemIdentifier = itemIdentifier;
        this.item = item;
        this.index = index;
        this.itemAction = itemAction;

        getOwner().addItem(this);
    }

    public String getItemIdentifier() {
        return itemIdentifier;
    }

    public Toolbar getOwner() {
        return owner;
    }

    public ItemStack getCopyOfItem() {
        return new ItemStack(item);
    }

    public int getInventoryIndex() {
        return this.index;
    }

    public AbstractToolAction getItemAction() {
        return itemAction;
    }

    public void trigger(final PlayerInteractEvent event) {
        // if the item action is null, don't do anything
        if (itemAction == null) {
            return;
        }
        itemAction.trigger(owner, event);
    }
}
