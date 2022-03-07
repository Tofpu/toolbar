package io.tofpu.umbrella.domain.item;

import org.bukkit.inventory.ItemStack;

public class UmbrellaItem {
    private final ItemStack item;

    public UmbrellaItem(final ItemStack item) {
        this.item = item;
    }

    public ItemStack getCopyOfItem() {
        return new ItemStack(item);
    }
}
