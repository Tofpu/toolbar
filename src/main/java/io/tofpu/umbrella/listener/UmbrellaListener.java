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
        if (UmbrellaAPI.getInstance()
                .isInModernVersion() && event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        final UmbrellaItem umbrellaItem = getUmbrellaItem(event.getItem());
        // if the umbrella item were found, pass over the event
        if (umbrellaItem != null) {
            umbrellaItem.trigger(event);
        }
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
