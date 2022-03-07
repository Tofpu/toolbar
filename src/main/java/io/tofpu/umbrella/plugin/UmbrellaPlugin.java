package io.tofpu.umbrella.plugin;

import io.tofpu.umbrella.UmbrellaAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class UmbrellaPlugin extends JavaPlugin {
    private final UmbrellaAPI umbrellaAPI;

    public UmbrellaPlugin() {
        this.umbrellaAPI = new UmbrellaAPI(this);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.umbrellaAPI.enable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.umbrellaAPI.disable();
    }
}
