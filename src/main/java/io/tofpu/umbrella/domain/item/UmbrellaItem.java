package io.tofpu.umbrella.domain.item;

import io.tofpu.umbrella.domain.Umbrella;
import io.tofpu.umbrella.domain.item.action.AbstractItemAction;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class UmbrellaItem {
    private final Umbrella owner;
    private final String itemIdentifier;
    private final ItemStack item;
    private final int index;

    private final AbstractItemAction itemAction;

    public UmbrellaItem(final Umbrella owner, final String itemIdentifier, final ItemStack item, final int index, final AbstractItemAction itemAction) {
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

    public Umbrella getOwner() {
        return owner;
    }

    public ItemStack getCopyOfItem() {
        return new ItemStack(item);
    }

    public void trigger(final PlayerInteractEvent event) {
        // if the item action is null, don't do anything
        if (itemAction == null) {
            return;
        }
        itemAction.trigger(owner, event);
    }

    public int getInventoryIndex() {
        return this.index;
    }
}
