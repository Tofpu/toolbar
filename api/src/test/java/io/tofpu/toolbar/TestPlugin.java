package io.tofpu.toolbar;

import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.toolbar.GenericToolbar;
import io.tofpu.toolbar.toolbar.ItemSlot;
import io.tofpu.toolbar.toolbar.ToolbarService;
import io.tofpu.toolbar.toolbar.tool.Tool;
import io.tofpu.toolbar.toolbar.tool.action.ToolAction;
import io.tofpu.toolbar.toolbar.tool.action.ToolActionTypes;
import io.tofpu.toolbar.toolbar.tool.action.ToolActionUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class TestPlugin extends JavaPlugin {
    private final ToolbarAPI toolbarAPI;

    public TestPlugin() {
        toolbarAPI = new ToolbarAPI(this);
    }

    public TestPlugin(Function<ItemStack, ItemNBTHandler> itemNBTHandlerFunction) {
        this.toolbarAPI = new ToolbarAPI(this, itemNBTHandlerFunction);
    }

    @Override
    public void onEnable() {
        toolbarAPI.enable();
        registerToolbars();
    }

    private void registerToolbars() {
        ToolbarService toolbarService = toolbarAPI.getToolbarService();
        // a list of staff tools, consisting of a kick and ban based tools
        List<StaffTool> staffTools = Arrays.asList(
                new StaffKickTool(ItemSlot.atIndex(0)),
                new StaffBanTool(ItemSlot.atIndex(1))
        );
        // constructs a staff-based toolbar consisting of the staff tools above
        StaffToolbar staffToolbar = new StaffToolbar("moderator-staff-bar", staffTools);
        // registers the staff-based toolbar to the registry for later use
        toolbarService.register(staffToolbar);
        System.out.println("registerToolbars() called");
    }

    public void equipModTools(Player player) {
        ToolbarService toolbarService = toolbarAPI.getToolbarService();
        // retrieves the moderator staff toolbar we registered earlier
        StaffToolbar toolbar = toolbarService.findToolbarBy("moderator-staff-bar");
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
        public StaffToolbar(String identifier, Collection<StaffTool> staffTools) {
            super(identifier, staffTools);
        }
    }

    /**
     * An umbrella class for staff-based tools.
     */
    static class StaffTool extends Tool {
        public StaffTool(String id, ItemStack item, ItemSlot slot, ToolAction<?> action) {
            super(id, item, slot, action);
        }
    }

    /**
     * A staff tool that kicks the interactor.
     */
    static class StaffKickTool extends StaffTool {
        public StaffKickTool(ItemSlot slot) {
            super("kick", new ItemStack(Material.STICK), slot, ToolActionUtil.listenFor(PlayerInteractEntityEvent.class, (owner, event) -> {
                Entity rightClicked = event.getRightClicked();
                if (rightClicked instanceof Player) {
                    ((Player) rightClicked).kick(Component.text("Kicked by staff!"));
                }
            }));
        }
    }

    /**
     * A staff tool that bans the interactor.
     */
    static class StaffBanTool extends StaffTool {
        public StaffBanTool(ItemSlot slot) {
            super("ban", new ItemStack(Material.TORCH), slot, (ToolActionTypes.PlayerEntityInteract) (owner, event) -> {
                Entity rightClicked = event.getRightClicked();
                if (rightClicked instanceof Player) {
                    ((Player) rightClicked).banPlayer("Banned by staff!");
                }
            });
        }
    }
}
