package io.tofpu.umbrella.listener;

import io.tofpu.umbrella.domain.item.UmbrellaItem;
import io.tofpu.umbrella.domain.service.UmbrellaService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import static io.tofpu.umbrella.listener.util.UmbrellaListenerUtil.getUmbrellaItem;
import static io.tofpu.umbrella.listener.util.UmbrellaListenerUtil.invalidItemDetected;

public class ProtectionListener implements Listener {
    private final UmbrellaService umbrellaService;

    public ProtectionListener(UmbrellaService umbrellaService) {
        this.umbrellaService = umbrellaService;
    }

    public void registerSelf(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onPlayerDropItemEvent(final PlayerDropItemEvent event) {
        final ItemStack droppedItem = event.getItemDrop()
                .getItemStack();
        final Player player = event.getPlayer();

        final UmbrellaItem umbrellaItem = getUmbrellaItem(droppedItem, umbrellaService);
        // if the umbrella item not were found, return
        if (umbrellaItem == null) {
            return;
        }

        if (!umbrellaService.getUmbrellaRegistry()
                .isInUmbrella(player.getUniqueId())) {
            invalidItemDetected(player, droppedItem, umbrellaService);
            return;
        }

        event.setCancelled(true);
        player.sendMessage(ChatColor.RED + "You're not allowed to drop this " + "item!");
    }
}
