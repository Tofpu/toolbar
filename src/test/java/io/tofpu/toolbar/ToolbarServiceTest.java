package io.tofpu.toolbar;

import io.tofpu.toolbar.bootstrap.FullTestBoostrap;
import io.tofpu.toolbar.toolbar.Toolbar;
import io.tofpu.toolbar.toolbar.ToolbarService;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.tofpu.toolbar.helper.ObjectCreationHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ToolbarServiceTest extends FullTestBoostrap {
    private ToolbarService toolbarService;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        toolbarService = api.getToolbarService();
    }

    @Test
    void register_toolbar_directly_with_no_items_test() {
        toolbarService.register(new Toolbar("bar"));
        Toolbar toolbar = toolbarService.findToolbarBy("bar");
        assertNotNull(toolbar);
        assertEquals("bar", toolbar.getIdentifier());
    }

    @Test
    void register_regular_toolbar_with_multiple_tools() {
        Toolbar toolbar = new Toolbar("bar", tools(
                tool("first_tool", Material.DIAMOND),
                tool("second_tool", Material.GOLD_ORE)
        ));
        toolbarService.register(toolbar);

        toolbar = toolbarService.findToolbarBy("bar");
        assertNotNull(toolbar);

        Map<Integer, ItemStack> itemStacks = toolbar.getItemStacks();
        assertEquals(2, itemStacks.size());

        assertEquals(Material.DIAMOND, itemStacks.get(0).getType());
        assertEquals(Material.GOLD_ORE, itemStacks.get(1).getType());
    }
}
