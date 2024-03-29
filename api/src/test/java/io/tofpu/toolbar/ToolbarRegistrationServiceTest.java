package io.tofpu.toolbar;

import io.tofpu.toolbar.bootstrap.FullTestBoostrap;
import io.tofpu.toolbar.toolbar.ItemSlot;
import io.tofpu.toolbar.toolbar.SimpleToolbar;
import io.tofpu.toolbar.toolbar.ToolbarRegistrationService;
import io.tofpu.toolbar.toolbar.tool.Tool;
import io.tofpu.toolbar.toolbar.tool.action.ToolActionUtil;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.tofpu.toolbar.helper.ObjectCreationHelper.tool;
import static io.tofpu.toolbar.helper.ObjectCreationHelper.tools;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ToolbarRegistrationServiceTest extends FullTestBoostrap {
    private ToolbarRegistrationService toolbarRegistrationService;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        toolbarRegistrationService = api.toolbarRegistrationService();
    }

    @Test
    void register_toolbar_directly_with_no_items_test() {
        toolbarRegistrationService.register(new SimpleToolbar("bar"));
        SimpleToolbar toolbar = toolbarRegistrationService.findToolbarBy("bar");
        assertNotNull(toolbar);
        assertEquals("bar", toolbar.getIdentifier());
    }

    @Test
    void register_regular_toolbar_with_multiple_tools() {
        SimpleToolbar toolbar = new SimpleToolbar("bar", tools(
                tool("first_tool", Material.DIAMOND),
                tool("second_tool", Material.GOLD_ORE)
        ));
        toolbarRegistrationService.register(toolbar);

        toolbar = toolbarRegistrationService.findToolbarBy("bar");
        assertNotNull(toolbar);

        assertEquals(2, toolbar.size());

        assertEquals(Material.DIAMOND, toolbar.getItemAt(ItemSlot.atIndex(0)).getType());
        assertEquals(Material.GOLD_ORE, toolbar.getItemAt(ItemSlot.atIndex(1)).getType());
    }

    @Test
    void register_custom_objects() {
        toolbarRegistrationService.register(new GunsBar("guns_bar"));
        SimpleToolbar toolbar = toolbarRegistrationService.findToolbarBy("guns_bar");
        assertNotNull(toolbar);

        toolbar.addItem(new GunTool("AR"));
        Tool tool = toolbar.findItemBy("AR");
        assertNotNull(tool);
    }

    class GunsBar extends SimpleToolbar {
        public GunsBar(String id) {
            super(id);

        }
    }

    class GunTool extends Tool {
        public GunTool(String id) {
            super(id, new ItemStack(Material.STICK), ToolActionUtil.listenFor(PlayerInteractEvent.class, (owner, event) -> event.getPlayer().sendMessage("You clicked a gun! Nice!")));
        }
    }
}
