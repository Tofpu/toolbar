package io.tofpu.umbrella.listener;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.tofpu.dynamicclass.meta.AutoRegister;
import io.tofpu.umbrella.UmbrellaAPI;
import io.tofpu.umbrella.domain.Umbrella;
import io.tofpu.umbrella.domain.item.UmbrellaItem;
import io.tofpu.umbrella.domain.service.UmbrellaService;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

@AutoRegister
public final class UmbrellaListener implements Listener {
    private final UmbrellaService umbrellaService;

    private UmbrellaListener(final Plugin plugin, final UmbrellaService umbrellaService) {
        this.umbrellaService = umbrellaService;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onPlayerInteract(final PlayerInteractEvent event) {
        // TODO: DO A VERSION CHECK
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        final ItemStack itemStack = event.getItem();
        if (itemStack == null) {
            return;
        }

        final NBTItem nbtItem = new NBTItem(itemStack);
        final String umbrellaIdentifier = nbtItem.getString("umbrella_identifier");
        final String itemIdentifier = nbtItem.getString("item_identifier");
        if (umbrellaIdentifier == null || itemIdentifier == null) {
            return;
        }

        final Umbrella umbrella = umbrellaService.findUmbrellaBy(umbrellaIdentifier);
        if (umbrella == null) {
            return;
        }

        final UmbrellaItem umbrellaItem = umbrella.findItemBy(umbrellaIdentifier);
        if (umbrellaItem == null) {
            return;
        }

        umbrellaItem.trigger(event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerQuit(final PlayerQuitEvent event) {
        UmbrellaAPI.getInstance().getUmbrellaService().inactivate(event.getPlayer());
    }
}
