package io.tofpu.toolbar;

import io.tofpu.toolbar.player.PlayerEquipService;
import io.tofpu.toolbar.toolbar.ToolbarService;
import org.bukkit.plugin.java.JavaPlugin;

public class ToolbarAPI {
    private static ToolbarAPI toolbarAPI;

    private final ToolbarService toolbarService;
    private final PlayerEquipService playerEquipService;
    private final JavaPlugin plugin;
    private final boolean modernVersion;

    public static ToolbarAPI getInstance() {
        return ToolbarAPI.toolbarAPI;
    }

    public ToolbarAPI(final JavaPlugin plugin) {
        this.toolbarService = new ToolbarService();
        this.playerEquipService = new PlayerEquipService();
        this.plugin = plugin;

        final String[] versionArgs = plugin.getServer()
                .getBukkitVersion()
                .split("-")[0].split("\\.");

        final String formattedVersion = versionArgs[0] + "." + versionArgs[1];

        final double version = Double.parseDouble(formattedVersion);
        this.modernVersion = version >= 1.9;
    }

    public void enable() {
        ToolbarAPI.toolbarAPI = this;

        new ToolbarAPIListener(plugin, this);
    }

    public void disable() {
        // nothing to disable
        playerEquipService.unequipAll();
        ToolbarAPI.toolbarAPI = null;
    }

    public boolean isInModernVersion() {
        return modernVersion;
    }

    public PlayerEquipService getPlayerEquipService() {
        return playerEquipService;
    }

    public ToolbarService getToolbarService() {
        return toolbarService;
    }
}
