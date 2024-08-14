package io.tofpu.umbrella.listener.util;

import io.tofpu.umbrella.UmbrellaAPI;
import io.tofpu.umbrella.domain.Umbrella;
import io.tofpu.umbrella.domain.item.UmbrellaItem;
import io.tofpu.umbrella.domain.nbt.ItemNBTHandler;
import io.tofpu.umbrella.domain.service.UmbrellaService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class UmbrellaListenerUtil {

    public static boolean isInvalidItem(final ItemStack target, final UmbrellaService umbrellaService) {
        if (target == null || target.getType() == Material.AIR) {
            return false;
        }

        final UmbrellaItem item = getUmbrellaItem(target, umbrellaService);
        return item != null;
    }

    public static void wipeInvalidItems(final Player target, final UmbrellaService umbrellaService) {
        final PlayerInventory inventory = target.getInventory();
        for (final ItemStack item : inventory) {
            if (!isInvalidItem(item, umbrellaService)) {
                continue;
            }
            inventory.remove(item);
        }

        final Inventory enderChest = target.getEnderChest();
        for (final ItemStack item : enderChest) {
            if (!isInvalidItem(item, umbrellaService)) {
                continue;
            }
            enderChest.remove(item);
        }

        final Inventory topInventory = target.getOpenInventory()
                .getTopInventory();
        for (final ItemStack item : topInventory) {
            if (!isInvalidItem(item, umbrellaService)) {
                continue;
            }
            topInventory.remove(item);
        }
    }

    public static void invalidItemDetected(final Player target, final ItemStack itemStack, final UmbrellaService umbrellaService) {
        itemStack.setType(Material.AIR);
        target.getInventory()
                .remove(itemStack);

        wipeInvalidItems(target, umbrellaService);
    }

    public static UmbrellaItem getUmbrellaItem(final ItemStack itemStack, final UmbrellaService umbrellaService) {
        if (itemStack == null) {
            return null;
        }

        ItemNBTHandler nbtHandler = UmbrellaAPI.getInstance().handleItemNBT(itemStack);
        final String itemIdentifier = nbtHandler.getString("item_identifier");
        if (itemIdentifier == null) {
            return null;
        }

        final Umbrella umbrella = getUmbrella(nbtHandler, itemStack, umbrellaService);
        if (umbrella == null) {
            return null;
        }

        return umbrella.findItemBy(itemIdentifier);
    }

    public static Umbrella getUmbrella(final ItemNBTHandler nbtHandler, final ItemStack itemStack, final UmbrellaService umbrellaService) {
        if (itemStack == null) {
            return null;
        }

        final String umbrellaIdentifier = nbtHandler.getString("umbrella_identifier");
        if (umbrellaIdentifier == null) {
            return null;
        }

        return umbrellaService.getUmbrellaRegistry().findUmbrellaBy(umbrellaIdentifier);
    }
}
