package io.tofpu.umbrella.listener;

import io.tofpu.dynamicclass.meta.AutoRegister;
import io.tofpu.umbrella.UmbrellaAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

@AutoRegister
public final class UmbrellaListener implements Listener {
    private UmbrellaListener(final Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerQuit(final PlayerQuitEvent event) {
        UmbrellaAPI.getInstance().getUmbrellaService().inactivate(event.getPlayer());
    }
}
