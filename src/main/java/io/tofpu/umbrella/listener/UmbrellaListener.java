package io.tofpu.umbrella.listener;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.tofpu.umbrella.UmbrellaAPI;
import io.tofpu.umbrella.domain.Umbrella;
import io.tofpu.umbrella.domain.item.UmbrellaItem;
import io.tofpu.umbrella.domain.service.UmbrellaService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public final class UmbrellaListener implements Listener {
    private final UmbrellaService umbrellaService;

    public UmbrellaListener(final Plugin plugin, final UmbrellaService umbrellaService) {
        this.umbrellaService = umbrellaService;
        Bukkit.getPluginManager()
                .registerEvents(this, plugin);
    }

    @EventHandler
    private void onPlayerDropItemEvent(final PlayerDropItemEvent event) {
        final ItemStack droppedItem = event.getItemDrop()
                .getItemStack();
        final Player player = event.getPlayer();

        final UmbrellaItem umbrellaItem = getUmbrellaItem(droppedItem);
        // if the umbrella item not were found, return
        if (umbrellaItem == null) {
            return;
        }

        if (!umbrellaService.getUmbrellaRegistry()
                .isInUmbrella(player.getUniqueId())) {
            invalidItemDetected(player, droppedItem);
            return;
        }

        event.setCancelled(true);
        player.sendMessage(ChatColor.RED + "You're not allowed to drop this " + "item!");
    }

    private void invalidItemDetected(final Player target, final ItemStack itemStack) {
        itemStack.setType(Material.AIR);
        target.getInventory()
                .remove(itemStack);

        wipeInvalidItems(target.getPlayer());
        target.sendMessage(
                ChatColor.DARK_RED + "ERROR!" + ChatColor.RED + "Every " + "invalid " +
                "item " + "in your inventory has been wiped.");
    }

    private void wipeInvalidItems(final Player target) {
        final PlayerInventory inventory = target.getInventory();
        for (final ItemStack item : inventory) {
            if (!isInvalidItem(item)) {
                continue;
            }
            inventory.remove(item);
        }

        final Inventory enderChest = target.getEnderChest();
        for (final ItemStack item : enderChest) {
            if (!isInvalidItem(item)) {
                continue;
            }
            enderChest.remove(item);
        }

        final Inventory topInventory = target.getOpenInventory()
                .getTopInventory();
        for (final ItemStack item : topInventory) {
            if (!isInvalidItem(item)) {
                continue;
            }
            topInventory.remove(item);
        }
    }

    private boolean isInvalidItem(final ItemStack target) {
        if (target == null || target.getType() == Material.AIR) {
            return false;
        }

        final UmbrellaItem item = getUmbrellaItem(target);
        return item != null;
    }

    @EventHandler
    private void onPlayerInteract(final PlayerInteractEvent event) {
        if (UmbrellaAPI.getInstance()
                    .isInModernVersion() && event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        final ItemStack clickedItem = event.getItem();
        final UmbrellaItem umbrellaItem = getUmbrellaItem(clickedItem);
        // if the umbrella item not were found, return
        if (umbrellaItem == null) {
            return;
        }

        final Player player = event.getPlayer();
        if (!umbrellaService.getUmbrellaRegistry()
                .isInUmbrella(player.getUniqueId())) {
            invalidItemDetected(player, clickedItem);
            return;
        }
        event.setCancelled(true);

        umbrellaItem.trigger(event);
    }

    private UmbrellaItem getUmbrellaItem(final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        final NBTItem nbtItem = new NBTItem(itemStack);
        final String itemIdentifier = nbtItem.getString("item_identifier");
        if (itemIdentifier == null) {
            return null;
        }

        final Umbrella umbrella = getUmbrella(nbtItem, itemStack);
        if (umbrella == null) {
            return null;
        }

        return umbrella.findItemBy(itemIdentifier);
    }

    private Umbrella getUmbrella(final NBTItem nbtItem, final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        final String umbrellaIdentifier = nbtItem.getString("umbrella_identifier");
        if (umbrellaIdentifier == null) {
            return null;
        }

        return umbrellaService.getUmbrellaRegistry().findUmbrellaBy(umbrellaIdentifier);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerQuit(final PlayerQuitEvent event) {
        UmbrellaAPI.getInstance()
                .getUmbrellaService()
                .getUmbrellaHandler().inactivate(event.getPlayer());
    }
}
