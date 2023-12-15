package io.tofpu.toolbar;

import io.tofpu.toolbar.domain.ToolbarService;
import org.bukkit.plugin.java.JavaPlugin;

public class ToolbarAPI {
    private static ToolbarAPI toolbarAPI;
    private final ToolbarService toolbarService;
    private final JavaPlugin plugin;
    private final boolean modernVersion;

    public static ToolbarAPI getInstance() {
        return ToolbarAPI.toolbarAPI;
    }

    public ToolbarAPI(final JavaPlugin plugin) {
        this.toolbarService = new ToolbarService();
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

        new ToolbarListener(plugin, toolbarService);
    }

    public void disable() {
        // nothing to disable
        ToolbarAPI.toolbarAPI = null;

        getToolbarService()
                .getToolbarHandler()
                .inactivateAll();
    }

    public boolean isInModernVersion() {
        return modernVersion;
    }

    public ToolbarService getToolbarService() {
        return toolbarService;
    }
}
