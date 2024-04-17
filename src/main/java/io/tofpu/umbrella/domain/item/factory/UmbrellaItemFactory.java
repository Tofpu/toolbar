package io.tofpu.umbrella.domain.item.factory;

import io.tofpu.umbrella.UmbrellaAPI;
import io.tofpu.umbrella.domain.Umbrella;
import io.tofpu.umbrella.domain.item.UmbrellaItem;
import io.tofpu.umbrella.domain.item.action.AbstractItemAction;
import io.tofpu.umbrella.domain.nbt.ItemNBTHandler;
import org.bukkit.inventory.ItemStack;

public final class UmbrellaItemFactory {
    public UmbrellaItem create(final Umbrella owner, final String itemIdentifier
            , final ItemStack item, final AbstractItemAction itemAction) {
        return create(owner, itemIdentifier, item, -1, itemAction);
    }

    public UmbrellaItem create(final Umbrella owner, final String itemIdentifier
            , final ItemStack item, final int index,
            final AbstractItemAction itemAction) {
        addMetadata(owner, itemIdentifier, item);

        return new UmbrellaItem(owner, itemIdentifier, item, index, itemAction);
    }

    private static void addMetadata(Umbrella owner, String itemIdentifier, ItemStack item) {
        ItemNBTHandler itemNBTHandler = UmbrellaAPI.getInstance().handleItemNBT(item);

        // adding the "umbrella_identifier" nbt tag to the item
        itemNBTHandler.setString("umbrella_identifier", owner.getIdentifier());

        // adding the "item_identifier" nbt tag to the item
        itemNBTHandler.setString("item_identifier", itemIdentifier);
    }
}
