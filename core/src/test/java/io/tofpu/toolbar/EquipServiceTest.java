package com.tofpu.toolbar;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.tofpu.toolbar.bootstrap.FullTestBoostrap;
import com.tofpu.toolbar.api.player.EquipService;
import com.tofpu.toolbar.api.toolbar.Toolbar;
import com.tofpu.toolbar.api.toolbar.ToolbarSchema;
import com.tofpu.toolbar.api.toolbar.ToolbarService;
import com.tofpu.toolbar.api.toolbar.tool.Tool;
import com.tofpu.toolbar.api.toolbar.tool.action.ToolAction;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tofpu.toolbar.helper.ToolCreationHelper.tool;
import static com.tofpu.toolbar.helper.ToolCreationHelper.withUndefinedSlot;
import static com.tofpu.toolbar.helper.ToolbarCreationHelper.toolbarSchema;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EquipServiceTest extends FullTestBoostrap {
    private ToolbarService toolbarService;
    private EquipService equipService;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        toolbarService = api.toolbarService();
        equipService = api.equipService();
    }

    @Test
    void equip_test() {
        PlayerMock player = server.addPlayer();

        Tool tool = tool("one", Material.DIAMOND_SWORD);
        ToolbarSchema toolbarSchema = toolbarSchema("bar", withUndefinedSlot(tool));
        Toolbar toolbar = toolbarService.register(toolbarSchema);

        assertTrue(equipService.equip(player, toolbar));
        assertTrue(equipService.isEquippingToolbar(player.getUniqueId()));
    }

    @Test
    void equip_and_interact_simulation_test() {
        PlayerMock player = server.addPlayer();

        Tool tool = tool("one", Material.DIAMOND_SWORD, ToolAction.of(PlayerInteractEvent.class, (owner, event) -> event.getPlayer().sendMessage("hi")));
        ToolbarSchema toolbarSchema = toolbarSchema("bar", withUndefinedSlot(tool));
        Toolbar toolbar = toolbarService.register(toolbarSchema);

        assertTrue(equipService.equip(player, toolbar));
        assertTrue(equipService.isEquippingToolbar(player.getUniqueId()));

        simulateInteract(player, tool);

        player.assertSaid("hi");
        player.assertNoMoreSaid();
    }

    private void simulateInteract(PlayerMock player, Tool tool) {
        PlayerInteractEvent interactEvent = new PlayerInteractEvent(player, Action.LEFT_CLICK_AIR, tool.item(), null, BlockFace.SELF);
        server.getPluginManager().callEvent(interactEvent);
    }
}
