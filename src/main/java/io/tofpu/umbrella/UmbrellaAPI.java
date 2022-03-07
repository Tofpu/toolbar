package io.tofpu.umbrella;

import io.tofpu.dynamicclass.DynamicClass;
import io.tofpu.umbrella.domain.service.UmbrellaService;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class UmbrellaAPI {
    private static UmbrellaAPI umbrellaAPI;
    private final UmbrellaService umbrellaService;
    private final JavaPlugin plugin;

    public static UmbrellaAPI getInstance() {
        return UmbrellaAPI.umbrellaAPI;
    }

    public UmbrellaAPI(final JavaPlugin plugin) {
        this.umbrellaService = new UmbrellaService();
        this.plugin = plugin;
    }

    public void enable() {
        UmbrellaAPI.umbrellaAPI = this;

        try {
            DynamicClass.addParameters(plugin, umbrellaService);
            DynamicClass.alternativeScan(getClass().getClassLoader(), "io.tofpu.umbrella");
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void disable() {
        // nothing to disable
        UmbrellaAPI.umbrellaAPI = null;
    }

    public UmbrellaService getUmbrellaService() {
        return umbrellaService;
    }
}
