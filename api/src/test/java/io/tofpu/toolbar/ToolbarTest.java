package io.tofpu.toolbar;

import io.tofpu.toolbar.bootstrap.FullTestBoostrap;
import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.toolbar.ItemSlot;
import io.tofpu.toolbar.toolbar.SimpleToolbar;
import io.tofpu.toolbar.toolbar.tool.Tool;
import io.tofpu.toolbar.toolbar.tool.action.ToolActionTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.tofpu.toolbar.helper.ObjectCreationHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ToolbarTest extends FullTestBoostrap {
    @Test
    void register_regular_toolbar_with_a_tool_containing_unspecified_index_test() {
        int itemAmount = 1;

        SimpleToolbar toolbar = new SimpleToolbar("bar", singleTool(
                tool("tool", new ItemStack(Material.DIAMOND, itemAmount))
        ));

        assertEquals(1, toolbar.size());

        ItemStack itemStack = toolbar.getItemAt(ItemSlot.atIndex(0));
        assertNotNull(itemStack);

        assertEquals(Material.DIAMOND, itemStack.getType());
        assertEquals(itemAmount, itemStack.getAmount());
//        new Tool("", item(Material.DIAMOND), (ToolAction.PlayerEntityInteract) (owner, event) -> {
//
//        });
//
//        new Tool("2", item(Material.DIAMOND), ToolAction.of(PlayerInteractEvent.class, (owner, event) -> {
//
//        }));

        new Tool("3", item(Material.DIAMOND), (ToolActionTypes.PlayerEntityInteract) (owner, event) -> {

        });
    }

    @Test
    void register_a_tool_containing_an_index_test() {
        int itemIndex = 5;
        int itemAmount = 1;

        SimpleToolbar toolbar = new SimpleToolbar("bar", withToolSlot(
                ItemSlot.atIndex(itemIndex),
                tool("tool", new ItemStack(Material.DIAMOND, itemAmount))
        ));

        Map<ItemSlot, ItemStack> itemStacks = toolbar.getItemsWithSlots();
        assertEquals(1, itemStacks.size());

        ItemStack itemStack = itemStacks.get(ItemSlot.atIndex(itemIndex));
        assertNotNull(itemStack);

        assertEquals(Material.DIAMOND, itemStack.getType());
        assertEquals(itemAmount, itemStack.getAmount());
    }

    @Test
    void item_nbt_after_added_in_constructor() {
        SimpleToolbar toolbar = new SimpleToolbar("bar", singleTool(tool("first_tool", Material.DIAMOND)));
        toolbar.getTools().forEach(tool -> {
            ItemNBTHandler nbtHandler = api.handleItemNBT(tool.getItem());
            assertEquals(toolbar.getIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOLBAR_NBT_KEY));
            assertEquals(tool.getItemIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOL_NBT_KEY));
        });
    }

    @Test
    void item_nbt_after_added_after_construction() {
        SimpleToolbar toolbar = new SimpleToolbar("bar", tools());
        toolbar.addItem(tool("first_tool", Material.DIAMOND));

        toolbar.getTools().forEach(tool -> {
            ItemNBTHandler nbtHandler = api.handleItemNBT(tool.getItem());
            assertEquals(toolbar.getIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOLBAR_NBT_KEY));
            assertEquals(tool.getItemIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOL_NBT_KEY));
        });
    }
}
