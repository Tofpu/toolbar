package io.tofpu.toolbar;

import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.toolbar.GenericToolbar;
import io.tofpu.toolbar.toolbar.ToolbarRegistrationService;
import io.tofpu.toolbar.toolbar.tool.Tool;
import io.tofpu.toolbar.toolbar.tool.action.ToolAction;
import io.tofpu.toolbar.toolbar.tool.action.ToolActionTypes;
import io.tofpu.toolbar.toolbar.tool.action.ToolActionUtil;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class TestPlugin extends JavaPlugin {
    private final ToolbarAPI toolbarAPI;

    public TestPlugin(@NotNull JavaPluginLoader loader, @NotNull PluginDescriptionFile description, @NotNull File dataFolder, @NotNull File file) {
        super(loader, description, dataFolder, file);
        toolbarAPI = new ToolbarAPI(this);
    }

    public TestPlugin(@NotNull JavaPluginLoader loader, @NotNull PluginDescriptionFile description, @NotNull File dataFolder, @NotNull File file, Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction) {
        super(loader, description, dataFolder, file);
        this.toolbarAPI = new ToolbarAPI(this, itemNBTHandlerFunction);
    }

    @Override
    public void onEnable() {
        toolbarAPI.enable();
        registerToolbars();
    }

    private void registerToolbars() {
        ToolbarRegistrationService toolbarRegistrationService = toolbarAPI.toolbarRegistrationService();
        // a list of staff tools, consisting of a kick and ban based tools
        List<StaffTool> staffTools = Arrays.asList(
                new StaffKickTool(),
                new StaffBanTool()
        );
        // constructs a staff-based toolbar consisting of the staff tools above
        StaffToolbar staffToolbar = new StaffToolbar("moderator-staff-bar", staffTools.toArray(new StaffTool[0]));
        // registers the staff-based toolbar to the registry for later use
        toolbarRegistrationService.register(staffToolbar);
        System.out.println("registerToolbars() called");
    }

    public void equipModTools(Player player) {
        ToolbarRegistrationService toolbarRegistrationService = toolbarAPI.toolbarRegistrationService();
        // retrieves the moderator staff toolbar we registered earlier
        StaffToolbar toolbar = toolbarRegistrationService.findToolbarBy("moderator-staff-bar");
        // equips the toolbar's items to the player
        toolbarAPI.getPlayerEquipService().equip(player, toolbar);
    }

    @Override
    public void onDisable() {
        if (toolbarAPI != null) {
            toolbarAPI.disable();
        }
    }

    /**
     * A staff-based toolbar which would consist of staff tools.
     */
    static class StaffToolbar extends GenericToolbar<StaffTool> {
        public StaffToolbar(String identifier, StaffTool[] tools) {
            super(identifier, tools);
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
            super("kick", new ItemStack(Material.STICK), ToolActionUtil.listenFor(PlayerInteractEntityEvent.class, (owner, event) -> {
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
