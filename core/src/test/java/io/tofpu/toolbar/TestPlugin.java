package com.tofpu.toolbar;

import com.tofpu.toolbar.core.ToolbarAPIImpl;
import com.tofpu.toolbar.api.nbt.ItemNBTHandler;
import com.tofpu.toolbar.api.toolbar.Toolbar;
import com.tofpu.toolbar.api.toolbar.ToolbarSchema;
import com.tofpu.toolbar.api.toolbar.ToolbarService;
import com.tofpu.toolbar.api.toolbar.tool.Tool;
import com.tofpu.toolbar.api.toolbar.tool.action.ToolAction;
import com.tofpu.toolbar.api.toolbar.tool.action.ToolActionTypes;
import org.apache.commons.lang.time.DateUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Date;
import java.util.function.Function;

public class TestPlugin extends JavaPlugin {
    private final ToolbarAPIImpl toolbarAPI;

    public TestPlugin(@NotNull JavaPluginLoader loader, @NotNull PluginDescriptionFile description, @NotNull File dataFolder, @NotNull File file) {
        super(loader, description, dataFolder, file);
        toolbarAPI = new ToolbarAPIImpl(this);
    }

    public TestPlugin(@NotNull JavaPluginLoader loader, @NotNull PluginDescriptionFile description, @NotNull File dataFolder, @NotNull File file, Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction) {
        super(loader, description, dataFolder, file);
        this.toolbarAPI = new ToolbarAPIImpl(this, itemNBTHandlerFunction, false);
    }

    @Override
    public void onEnable() {
        toolbarAPI.load();
        registerToolbars();
    }

    private void registerToolbars() {
        ToolbarService toolbarService = toolbarAPI.toolbarService();
        // constructs a staff-based toolbar consisting of the staff tools above
        ToolbarSchema toolbarSchema = ToolbarSchema.newBuilder("moderator-staff-bar")
                // a list of staff tools, consisting of a kick and ban based tools
                .addItems(new StaffKickTool(), new StaffBanTool())
                .build();

        // registers the staff-based toolbar to the registry for later use
        toolbarService.register(toolbarSchema);
        System.out.println("registerToolbars() called");
    }

    public void equipModTools(Player player) {
        ToolbarService toolbarService = toolbarAPI.toolbarService();
        // retrieves the moderator staff toolbar we registered earlier
        Toolbar toolbar = toolbarService.findToolbarBy("moderator-staff-bar");
        // equips the toolbar's items to the player
        toolbarAPI.equipService().equip(player, toolbar);
    }

    @Override
    public void onDisable() {
        if (toolbarAPI != null) {
            toolbarAPI.disable();
        }
    }

    /**
     * An umbrella class for staff-based tools.
     */
    static class StaffTool extends Tool {
        public StaffTool(String id, ItemStack item, ToolAction<?> action) {
            super(id, item, action);
        }
    }

    /**
     * A staff tool that kicks the interactor.
     */
    static class StaffKickTool extends StaffTool {
        public StaffKickTool() {
            super("kick", new ItemStack(Material.STICK), ToolAction.of(PlayerInteractEntityEvent.class, (owner, event) -> {
                Entity rightClicked = event.getRightClicked();
                if (rightClicked instanceof Player) {
                    ((Player) rightClicked).kickPlayer("Kicked by staff");
                }
            }));
        }
    }

    /**
     * A staff tool that bans the interactor.
     */
    static class StaffBanTool extends StaffTool {
        public StaffBanTool() {
            super("ban", new ItemStack(Material.TORCH), (ToolActionTypes.PlayerEntityInteract) (owner, event) -> {
                Entity rightClicked = event.getRightClicked();
                if (rightClicked instanceof Player) {
                    // this is obviously not ideal as we weren't using their id but this is just an illustration
                    Bukkit.getBanList(BanList.Type.NAME).addBan(rightClicked.getName(), "Banned by staff", DateUtils.addDays(new Date(), 31), "plugin");
                    // we kick the player manually as the #addBan doesn't do it for us
                    ((Player) rightClicked).kickPlayer("Banned by staff!");
                }
            });
        }
    }
}
