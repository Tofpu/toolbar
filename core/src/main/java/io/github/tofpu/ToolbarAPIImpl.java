package io.github.tofpu;

import io.github.tofpu.util.PlatformVersion;
import io.tofpu.toolbar.PlatformMetadata;
import io.tofpu.toolbar.ToolbarAPI;
import io.tofpu.toolbar.listener.ListenerService;
import io.tofpu.toolbar.logger.Logger;
import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.player.EquipService;
import io.tofpu.toolbar.toolbar.ToolbarService;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;

public class ToolbarAPIImpl implements ToolbarAPI {
    private final PlatformMetadataImpl platformMetadata;
    private final ToolbarAPIHandler handler;
    private final Logger logger;

    public ToolbarAPIImpl(final JavaPlugin plugin) {
        this(plugin, false);
    }

    public ToolbarAPIImpl(final JavaPlugin plugin, boolean verbose) {
        this(plugin, itemStack -> ToolbarAPIHandler.determineSuitableNBTHandler(plugin, itemStack), verbose);
    }

    public ToolbarAPIImpl(final JavaPlugin plugin, final Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction, boolean verbose) {
        this.platformMetadata = new PlatformMetadataImpl(new PlatformVersion(plugin.getServer().getBukkitVersion()));
        this.handler = new ToolbarAPIHandler(plugin, itemNBTHandlerFunction);
        this.logger = new Logger(plugin.getLogger(), verbose);
    }

    public void enable() {
        handler.enable(this);
    }

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
