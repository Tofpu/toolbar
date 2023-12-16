package io.tofpu.toolbar;

import io.tofpu.toolbar.toolbar.GenericToolbar;
import io.tofpu.toolbar.toolbar.tool.Tool;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
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

        removeAllItems(target);
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
    private void on(final PlayerInteractEvent event) {
        if (isNotMainHand(event)) return;
        handle(event, event.getPlayer(), event.getItem());
    }

    @EventHandler
    private void on(final PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        handle(event, player, player.getActiveItem());
    }

    private static boolean isNotMainHand(PlayerInteractEvent event) {
        return ToolbarAPI.getInstance()
                .isInModernVersion() && event.getHand() != EquipmentSlot.HAND;
    }

    private void handle(Event event, Player clicker, ItemStack clickedItem) {
        if (clickedItem == null) return;

        final GenericToolbar<?> toolbar = api.getToolbarService().findToolbarBy(ToolNBTUtil.getToolbarIdBy(clickedItem));
        if (toolbar == null) return;

        final Tool tool = toolbar.findItemBy(ToolNBTUtil.getToolIdBy(clickedItem));
        if (tool == null) return;

        if (!api.getPlayerEquipService().isEquippingToolbar(clicker.getUniqueId())) {
            removeAllTrace(clicker, clickedItem);
            return;
        }

        if (event instanceof Cancellable) {
            ((Cancellable) event).setCancelled(true);
        }
        tool.trigger(toolbar, event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerQuit(final PlayerQuitEvent event) {
        ToolbarAPI.getInstance()
                .getPlayerEquipService().unequip(event.getPlayer());
    }
}
