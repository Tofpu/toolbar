package io.tofpu.umbrella.listener.interaction;

import io.tofpu.umbrella.UmbrellaAPI;
import io.tofpu.umbrella.domain.item.UmbrellaItem;
import io.tofpu.umbrella.domain.service.UmbrellaService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import static io.tofpu.umbrella.listener.util.UmbrellaListenerUtil.getUmbrellaItem;
import static io.tofpu.umbrella.listener.util.UmbrellaListenerUtil.invalidItemDetected;

public final class BukkitInteractionListener implements Listener {
    private final UmbrellaService umbrellaService;

    public BukkitInteractionListener(final Plugin plugin, final UmbrellaService umbrellaService) {
        this.umbrellaService = umbrellaService;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onPlayerInteract(final PlayerInteractEvent event) {
        if (UmbrellaAPI.getInstance()
                    .isInModernVersion() && event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        final ItemStack clickedItem = event.getItem();
        final UmbrellaItem umbrellaItem = getUmbrellaItem(clickedItem, umbrellaService);
        // if the umbrella item not were found, return
        if (umbrellaItem == null) {
            return;
        }

        final Player player = event.getPlayer();
        if (!umbrellaService.getUmbrellaRegistry()
                .isInUmbrella(player.getUniqueId())) {
            invalidItemDetected(player, clickedItem, umbrellaService);
            return;
        }
        event.setCancelled(true);

        umbrellaItem.trigger(event);
    }
}
