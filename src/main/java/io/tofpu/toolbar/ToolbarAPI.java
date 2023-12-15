package io.tofpu.toolbar;

import io.tofpu.toolbar.nbt.BukkitNBTHandler;
import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.player.PlayerEquipService;
import io.tofpu.toolbar.toolbar.ToolbarService;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;

public class ToolbarAPI {
    private static ToolbarAPI toolbarAPI;

    private final Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction;
    private final ToolbarService toolbarService;
    private final PlayerEquipService playerEquipService;
    private final JavaPlugin plugin;
    private final boolean modernVersion;

    public ToolbarAPI(final JavaPlugin plugin) {
        this(plugin, BukkitNBTHandler::new);
    }

    public ToolbarAPI(final JavaPlugin plugin, final Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction) {
        this.itemNBTHandlerFunction = itemNBTHandlerFunction;
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

    public static ToolbarAPI getInstance() {
        return ToolbarAPI.toolbarAPI;
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

    public ItemNBTHandler handleItemNBT(ItemStack itemStack) {
        return itemNBTHandlerFunction.apply(itemStack);
    }

    public PlayerEquipService getPlayerEquipService() {
        return playerEquipService;
    }

    public ToolbarService getToolbarService() {
        return toolbarService;
    }
}
