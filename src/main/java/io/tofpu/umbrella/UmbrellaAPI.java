package io.tofpu.umbrella;

import io.tofpu.umbrella.domain.service.UmbrellaService;
import io.tofpu.umbrella.listener.UmbrellaListener;
import org.bukkit.plugin.java.JavaPlugin;

public class UmbrellaAPI {
    private static UmbrellaAPI umbrellaAPI;
    private final UmbrellaService umbrellaService;
    private final JavaPlugin plugin;
    private final boolean modernVersion;

    public static UmbrellaAPI getInstance() {
        return UmbrellaAPI.umbrellaAPI;
    }

    public UmbrellaAPI(final JavaPlugin plugin) {
        this.umbrellaService = new UmbrellaService();
        this.plugin = plugin;

        final String[] versionArgs = plugin.getServer()
                .getBukkitVersion()
                .split("-")[0].split("\\.");

        final String formattedVersion = versionArgs[0] + "." + versionArgs[1];

        final double version = Double.parseDouble(formattedVersion);
        this.modernVersion = version >= 1.9;
    }

    public void enable() {
        UmbrellaAPI.umbrellaAPI = this;

        new UmbrellaListener(plugin, umbrellaService);
    }

    public void disable() {
        // nothing to disable
        UmbrellaAPI.umbrellaAPI = null;

        getUmbrellaService()
                .getUmbrellaHandler()
                .inactivateAll();
    }

    public boolean isInModernVersion() {
        return modernVersion;
    }

    public UmbrellaService getUmbrellaService() {
        return umbrellaService;
    }
}
