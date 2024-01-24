package io.tofpu.toolbar;

import io.tofpu.toolbar.listener.ListenerService;
import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.player.PlayerEquipService;
import io.tofpu.toolbar.toolbar.GenericToolbar;
import io.tofpu.toolbar.toolbar.ToolbarRegistrationService;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;

public class ToolbarAPI {
    private static ToolbarAPI toolbarAPI;

    private final ToolbarAPIHandler handler;

    public ToolbarAPI(final JavaPlugin plugin) {
        this(plugin, itemStack -> ToolbarAPIHandler.determineSuitableNBTHandler(plugin, itemStack));
    }

    public ToolbarAPI(final JavaPlugin plugin, final Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction) {
        this.handler = new ToolbarAPIHandler(plugin, itemNBTHandlerFunction);
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

    public boolean unequip(Player player) {
        return getPlayerEquipService().unequip(player);
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
}
