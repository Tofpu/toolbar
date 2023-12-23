package io.tofpu.toolbar;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.tofpu.toolbar.bootstrap.FullTestBoostrap;
import io.tofpu.toolbar.player.PlayerEquipService;
import io.tofpu.toolbar.toolbar.SimpleToolbar;
import io.tofpu.toolbar.toolbar.ToolbarService;
import io.tofpu.toolbar.toolbar.tool.Tool;
import io.tofpu.toolbar.toolbar.tool.action.ToolAction;
import io.tofpu.toolbar.toolbar.tool.action.ToolActionUtil;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.tofpu.toolbar.helper.ObjectCreationHelper.singleTool;
import static io.tofpu.toolbar.helper.ObjectCreationHelper.tool;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerEquipServiceTest extends FullTestBoostrap {
    private ToolbarService toolbarService;
    private PlayerEquipService playerEquipService;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        toolbarService = api.getToolbarService();
        playerEquipService = api.getPlayerEquipService();
    }

    @Test
    void equip_test() {
        PlayerMock player = server.addPlayer();

        SimpleToolbar toolbar = new SimpleToolbar("bar", singleTool(
                tool("one", Material.DIAMOND_SWORD)
        ));
        toolbarService.register(toolbar);

        assertTrue(playerEquipService.equip(player, toolbar));
        assertTrue(playerEquipService.isEquippingToolbar(player.getUniqueId()));
    }

    @Test
    void equip_and_interact_simulation_test() {
        PlayerMock player = server.addPlayer();

        Tool tool = tool("one", Material.DIAMOND_SWORD, ToolActionUtil.listenFor(PlayerInteractEvent.class, (owner, event) -> event.getPlayer().sendMessage("hi")));
        SimpleToolbar toolbar = new SimpleToolbar("bar", singleTool(tool));
        toolbarService.register(toolbar);

        assertTrue(playerEquipService.equip(player, toolbar));
        assertTrue(playerEquipService.isEquippingToolbar(player.getUniqueId()));

        simulateInteract(player, tool);

        player.assertSaid("hi");
        player.assertNoMoreSaid();
    }

    private void simulateInteract(PlayerMock player, Tool tool) {
        PlayerInteractEvent interactEvent = new PlayerInteractEvent(player, Action.LEFT_CLICK_AIR, tool.getItem(), null, BlockFace.SELF);
        server.getPluginManager().callEvent(interactEvent);
    }
}