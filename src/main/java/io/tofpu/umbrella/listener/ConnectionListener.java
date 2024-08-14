package io.tofpu.umbrella.listener;

import io.tofpu.umbrella.UmbrellaAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class ConnectionListener implements Listener {
    public void registerSelf(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerQuit(final PlayerQuitEvent event) {
        UmbrellaAPI.getInstance()
                .getUmbrellaService()
                .getUmbrellaHandler().inactivate(event.getPlayer());
    }
}
