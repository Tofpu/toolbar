package com.tofpu.toolbar.core;

import com.tofpu.toolbar.api.PlatformMetadata;
import com.tofpu.toolbar.api.ToolbarAPI;
import com.tofpu.toolbar.api.listener.ListenerService;
import com.tofpu.toolbar.api.logger.Logger;
import com.tofpu.toolbar.api.nbt.ItemNBTHandler;
import com.tofpu.toolbar.api.player.EquipService;
import com.tofpu.toolbar.api.toolbar.ToolbarService;
import com.tofpu.toolbar.core.settings.Settings;
import com.tofpu.toolbar.core.util.PlatformVersion;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;

public class ToolbarAPIImpl implements ToolbarAPI {
    private final PlatformMetadataImpl platformMetadata;
    private final ToolbarAPIHandler handler;
    private final Logger logger;

    public ToolbarAPIImpl(final JavaPlugin plugin) {
        this(plugin, Settings.newBuilder().build());
    }

    public ToolbarAPIImpl(final JavaPlugin plugin, final Settings settings) {
        this(plugin, itemStack -> ToolbarAPIHandler.determineSuitableNBTHandler(plugin, itemStack), settings);
    }

    public ToolbarAPIImpl(final JavaPlugin plugin, final Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction, final Settings settings) {
        this.platformMetadata = new PlatformMetadataImpl(new PlatformVersion(plugin.getServer().getBukkitVersion()));
        this.handler = new ToolbarAPIHandler(plugin, itemNBTHandlerFunction, settings.packages());
        this.logger = new Logger(plugin.getLogger(), settings.verbose());
    }

    @Override
    public void load() {
        handler.enable(this);
    }

    @Override
    public void disable() {
        // nothing to disable
        handler.disable();
    }

    public <T extends Event> void notListeningWarn(Class<T> clazz) {
        handler.notListeningWarn(clazz);
    }

    @Override
    public EquipService equipService() {
        return handler.getPlayerEquipService();
    }

    @Override
    public ToolbarService toolbarService() {
        return handler.getToolbarRegistrationService();
    }

    @Override
    public ListenerService listenerService() {
        return handler.listenerService();
    }

    @Override
    public ItemNBTHandler itemNBTHandler(ItemStack itemStack) {
        return handler.handleItemNBT(itemStack);
    }

    @Override
    public PlatformMetadata platformMetadata() {
        return platformMetadata;
    }

    public Logger logger() {
        return logger;
    }
}
