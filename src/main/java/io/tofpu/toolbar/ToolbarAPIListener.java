package io.tofpu.toolbar;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.tofpu.toolbar.toolbar.Toolbar;
import io.tofpu.toolbar.toolbar.item.Tool;
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

class ToolbarAPIListener implements Listener {
    private final ToolbarAPI api;

    public ToolbarAPIListener(final Plugin plugin, ToolbarAPI api) {
        this.api = api;
        Bukkit.getPluginManager()
                .registerEvents(this, plugin);
    }

    @EventHandler
    private void onPlayerDropItemEvent(final PlayerDropItemEvent event) {
        final ItemStack droppedItem = event.getItemDrop()
                .getItemStack();
        final Player player = event.getPlayer();

        final Tool tool = getTool(droppedItem);
        // if the umbrella item not were found, return
        if (tool == null) {
            return;
        }

        if (!api.getPlayerEquipService()
                .isEquippingToolbar(player.getUniqueId())) {
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

        final Tool item = getTool(target);
        return item != null;
    }

    @EventHandler
    private void onPlayerInteract(final PlayerInteractEvent event) {
        if (ToolbarAPI.getInstance()
                    .isInModernVersion() && event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        final ItemStack clickedItem = event.getItem();
        final Tool tool = getTool(clickedItem);
        // if the umbrella item not were found, return
        if (tool == null) {
            return;
        }

        final Player player = event.getPlayer();
        if (!api.getPlayerEquipService().isEquippingToolbar(player.getUniqueId())) {
            invalidItemDetected(player, clickedItem);
            return;
        }
        event.setCancelled(true);

        tool.trigger(event);
    }

    private Tool getTool(final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        final NBTItem nbtItem = new NBTItem(itemStack);
        final String itemIdentifier = nbtItem.getString("item_identifier");
        if (itemIdentifier == null) {
            return null;
        }

        final Toolbar toolbar = getToolbar(nbtItem, itemStack);
        if (toolbar == null) {
            return null;
        }

        return toolbar.findItemBy(itemIdentifier);
    }

    private Toolbar getToolbar(final NBTItem nbtItem, final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        final String umbrellaIdentifier = nbtItem.getString("umbrella_identifier");
        if (umbrellaIdentifier == null) {
            return null;
        }

        return api.getToolbarService().getToolbarRegistry().findToolbarBy(umbrellaIdentifier);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerQuit(final PlayerQuitEvent event) {
        ToolbarAPI.getInstance()
                .getPlayerEquipService().unequip(event.getPlayer());
    }
}
