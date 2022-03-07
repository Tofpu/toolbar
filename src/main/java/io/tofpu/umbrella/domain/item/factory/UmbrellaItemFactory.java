package io.tofpu.umbrella.domain.item.factory;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.tofpu.umbrella.domain.Umbrella;
import io.tofpu.umbrella.domain.item.UmbrellaItem;
import io.tofpu.umbrella.domain.item.action.AbstractItemAction;
import org.bukkit.inventory.ItemStack;

public final class UmbrellaItemFactory {
    public UmbrellaItem create(final Umbrella owner, final String itemIdentifier
            , final ItemStack item, final AbstractItemAction itemAction) {
        final NBTItem nbtItem = new NBTItem(item, true);

        // adding the "umbrella_identifier" nbt tag to the item
        nbtItem.setString("umbrella_identifier", owner.getIdentifier());

        // adding the "item_identifier" nbt tag to the item
        nbtItem.setString("item_identifier", itemIdentifier);

        return new UmbrellaItem(owner, itemIdentifier, item, itemAction);
    }
}
