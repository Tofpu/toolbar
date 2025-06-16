package io.tofpu.toolbar;

import io.tofpu.toolbar.listener.ListenerService;
import io.tofpu.toolbar.logger.Logger;
import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.player.PlayerEquipService;
import io.tofpu.toolbar.toolbar.GenericToolbar;
import io.tofpu.toolbar.toolbar.ToolbarRegistrationService;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.function.Function;

public class ToolbarAPI {
    private static ToolbarAPI toolbarAPI;

    private final ToolbarAPIHandler handler;
    private final Logger logger;

    public ToolbarAPI(final JavaPlugin plugin) {
        this(plugin, false);
    }

    public ToolbarAPI(final JavaPlugin plugin, boolean verbose) {
        this(plugin, itemStack -> ToolbarAPIHandler.determineSuitableNBTHandler(plugin, itemStack), verbose);
    }

    public ToolbarAPI(final JavaPlugin plugin, final Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction, boolean verbose) {
        this.handler = new ToolbarAPIHandler(plugin, itemNBTHandlerFunction);
        this.logger = new Logger(plugin.getLogger(), verbose);
    }

    public static ToolbarAPI getInstance() {
        return ToolbarAPI.toolbarAPI;
    }

    public void enable() {
        ToolbarAPI.toolbarAPI = this;

        handler.enable(this);
    }

    public void disable() {
        // nothing to disable
        handler.disable();
        ToolbarAPI.toolbarAPI = null;
    }

    public void registerToolbar(GenericToolbar<?> toolbar) {
        toolbarRegistrationService().register(toolbar);
    }

    public <T extends GenericToolbar<?>> T findToolbarBy(final String identifier) {
        return toolbarRegistrationService().findToolbarBy(identifier);
    }

    public boolean equip(final String toolbarIdentifier, final Player player) {
        return getPlayerEquipService().equip(player, findToolbarBy(toolbarIdentifier));
    }

    public boolean unequip(final Player player) {
        return getPlayerEquipService().unequip(player);
    }

    public boolean unequip(final UUID playerId) {
        return getPlayerEquipService().unequip(playerId);
    }

    public <T extends Event> void notListeningWarn(Class<T> clazz) {
        handler.notListeningWarn(clazz);
    }

    public boolean isInModernVersion() {
        return handler.isInModernVersion();
    }

    public ItemNBTHandler handleItemNBT(ItemStack itemStack) {
        return handler.handleItemNBT(itemStack);
    }

    public PlayerEquipService getPlayerEquipService() {
        return handler.getPlayerEquipService();
    }

    public ToolbarRegistrationService toolbarRegistrationService() {
        return handler.getToolbarRegistrationService();
    }

    public ListenerService listenerService() {
        return handler.listenerService();
    }

    public Logger logger() {
        return logger;
    }
}
