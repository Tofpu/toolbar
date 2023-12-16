package io.tofpu.toolbar;

import io.tofpu.toolbar.bootstrap.FullTestBoostrap;
import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.toolbar.Toolbar;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.tofpu.toolbar.helper.ObjectCreationHelper.*;
import static io.tofpu.toolbar.helper.ObjectCreationHelper.tool;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ToolbarTest extends FullTestBoostrap {
    @Test
    void register_regular_toolbar_with_a_tool_containing_unspecified_index_test() {
        int itemAmount = 1;

        Toolbar toolbar = new Toolbar("bar", singleTool(
                tool("tool", new ItemStack(Material.DIAMOND, itemAmount))
        ));

        Map<Integer, ItemStack> itemStacks = toolbar.getItemStacks();
        assertEquals(1, itemStacks.size());

        ItemStack itemStack = itemStacks.get(0);
        assertNotNull(itemStack);

        assertEquals(Material.DIAMOND, itemStack.getType());
        assertEquals(itemAmount, itemStack.getAmount());
    }

    @Test
    void register_a_tool_containing_an_index_test() {
        int itemIndex = 5;
        int itemAmount = 1;

        Toolbar toolbar = new Toolbar("bar", singleTool(
                tool("tool", new ItemStack(Material.DIAMOND, itemAmount), itemIndex)
        ));

        Map<Integer, ItemStack> itemStacks = toolbar.getItemStacks();
        assertEquals(1, itemStacks.size());

        ItemStack itemStack = itemStacks.get(itemIndex);
        assertNotNull(itemStack);

        assertEquals(Material.DIAMOND, itemStack.getType());
        assertEquals(itemAmount, itemStack.getAmount());
    }

    @Test
    void item_nbt_after_added_in_constructor() {
        Toolbar toolbar = new Toolbar("bar", singleTool(tool("first_tool", Material.DIAMOND)));
        toolbar.getTools().forEach(tool -> {
            ItemNBTHandler nbtHandler = api.handleItemNBT(tool.getItem());
            assertEquals(toolbar.getIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOLBAR_NBT_KEY));
            assertEquals(tool.getItemIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOL_NBT_KEY));
        });
    }

    @Test
    void item_nbt_after_added_after_construction() {
        Toolbar toolbar = new Toolbar("bar", tools());
        toolbar.addItem(tool("first_tool", Material.DIAMOND));

        toolbar.getTools().forEach(tool -> {
            ItemNBTHandler nbtHandler = api.handleItemNBT(tool.getItem());
            assertEquals(toolbar.getIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOLBAR_NBT_KEY));
            assertEquals(tool.getItemIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOL_NBT_KEY));
        });
    }
}
