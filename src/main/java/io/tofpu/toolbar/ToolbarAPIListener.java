package io.tofpu.toolbar;

import io.tofpu.toolbar.toolbar.Toolbar;
import io.tofpu.toolbar.toolbar.tool.Tool;
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

        if (ToolNBTUtil.isNotTool(droppedItem)) {
            return;
        }

        if (!api.getPlayerEquipService()
                .isEquippingToolbar(player.getUniqueId())) {
            removeAllTrace(player, droppedItem);
            return;
        }

        event.setCancelled(true);
        player.sendMessage(ChatColor.RED + "You're not allowed to drop this " + "item!");
    }

    private void removeAllTrace(final Player target, final ItemStack itemStack) {
        itemStack.setType(Material.AIR);
        target.getInventory()
                .remove(itemStack);

        removeAllItems(target.getPlayer());
    }

    private void removeAllItems(final Player target) {
        final PlayerInventory inventory = target.getInventory();
        for (final ItemStack item : inventory) {
            if (ToolNBTUtil.isNotTool(item)) {
                continue;
            }
            inventory.remove(item);
        }

        final Inventory enderChest = target.getEnderChest();
        for (final ItemStack item : enderChest) {
            if (ToolNBTUtil.isNotTool(item)) {
                continue;
            }
            enderChest.remove(item);
        }

        final Inventory topInventory = target.getOpenInventory()
                .getTopInventory();
        for (final ItemStack item : topInventory) {
            if (ToolNBTUtil.isNotTool(item)) {
                continue;
            }
            topInventory.remove(item);
        }
    }

    @EventHandler
    private void onPlayerInteract(final PlayerInteractEvent event) {
        if (ToolbarAPI.getInstance()
                .isInModernVersion() && event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        final ItemStack clickedItem = event.getItem();

        final Toolbar toolbar = api.getToolbarService().findToolbarBy(ToolNBTUtil.getToolbarIdBy(clickedItem));
        if (toolbar == null) return;

        final Tool tool = toolbar.findItemBy(ToolNBTUtil.getToolIdBy(clickedItem));
        if (tool == null) return;

        final Player player = event.getPlayer();
        if (!api.getPlayerEquipService().isEquippingToolbar(player.getUniqueId())) {
            removeAllTrace(player, clickedItem);
            return;
        }
        event.setCancelled(true);
        tool.trigger(toolbar, event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerQuit(final PlayerQuitEvent event) {
        ToolbarAPI.getInstance()
                .getPlayerEquipService().unequip(event.getPlayer());
    }
}
