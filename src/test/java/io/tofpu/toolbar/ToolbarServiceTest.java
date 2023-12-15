package io.tofpu.toolbar;

import io.tofpu.toolbar.bootstrap.FullTestBoostrap;
import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.toolbar.Toolbar;
import io.tofpu.toolbar.toolbar.ToolbarService;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.tofpu.toolbar.helper.ObjectCreationHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ToolbarServiceTest extends FullTestBoostrap {
    private final ToolbarService toolbarService = new ToolbarService();

    @Test
    void register_toolbar_directly_with_no_items_test() {
        toolbarService.register(new Toolbar("bar"));
        Toolbar toolbar = toolbarService.findToolbarBy("bar");
        assertNotNull(toolbar);
        assertEquals("bar", toolbar.getIdentifier());
    }

    @Test
    void register_regular_toolbar_with_a_tool_containing_unspecified_index_test() {
        int itemAmount = 1;

        Toolbar toolbar = new Toolbar("bar", singleTool(
                tool("tool", new ItemStack(Material.DIAMOND, itemAmount))
        ));
        toolbarService.register(toolbar);

        toolbar = toolbarService.findToolbarBy("bar");
        assertNotNull(toolbar);

        Map<Integer, ItemStack> itemStacks = toolbar.getItemStacks();
        assertEquals(1, itemStacks.size());

        ItemStack itemStack = itemStacks.get(0);
        assertNotNull(itemStack);

        assertEquals(Material.DIAMOND, itemStack.getType());
        assertEquals(itemAmount, itemStack.getAmount());
    }

    @Test
    void register_regular_toolbar_with_a_tool_containing_an_index_test() {
        int itemIndex = 5;
        int itemAmount = 1;

        Toolbar toolbar = new Toolbar("bar", singleTool(
                tool("tool", new ItemStack(Material.DIAMOND, itemAmount), itemIndex)
        ));
        toolbarService.register(toolbar);

        toolbar = toolbarService.findToolbarBy("bar");
        assertNotNull(toolbar);

        Map<Integer, ItemStack> itemStacks = toolbar.getItemStacks();
        assertEquals(1, itemStacks.size());

        ItemStack itemStack = itemStacks.get(itemIndex);
        assertNotNull(itemStack);

        assertEquals(Material.DIAMOND, itemStack.getType());
        assertEquals(itemAmount, itemStack.getAmount());
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

    @Test
    void item_nbt_after_added_in_construction() {
        Toolbar toolbar = new Toolbar("bar", singleTool(tool("first_tool", Material.DIAMOND)));
        toolbarService.register(toolbar);

        toolbar.getCopyItemMap().values().forEach(tool -> {
            ItemNBTHandler nbtHandler = api.handleItemNBT(tool.getItem());
            assertEquals(toolbar.getIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOLBAR_NBT_KEY));
            assertEquals(tool.getItemIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOL_NBT_KEY));
        });
    }

    @Test
    void item_nbt_after_added_after_construction() {
        Toolbar toolbar = new Toolbar("bar", tools());
        toolbarService.register(toolbar);

        toolbar.addItem(tool("first_tool", Material.DIAMOND));

        toolbar.getCopyItemMap().values().forEach(tool -> {
            ItemNBTHandler nbtHandler = api.handleItemNBT(tool.getItem());
            assertEquals(toolbar.getIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOLBAR_NBT_KEY));
            assertEquals(tool.getItemIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOL_NBT_KEY));
        });
    }
}
