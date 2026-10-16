//package com.tofpu.toolbar;
//
//import com.tofpu.toolbar.ToolbarImpl;
//import com.tofpu.toolbar.bootstrap.FullTestBoostrap;
//import com.tofpu.toolbar.api.toolbar.ItemSlot;
//import com.tofpu.toolbar.api.toolbar.Toolbar;
//import org.bukkit.Material;
//import org.bukkit.inventory.ItemStack;
//import org.junit.jupiter.api.Test;
//
//import java.util.Map;
//
//import static com.tofpu.toolbar.helper.ToolCreationHelper.*;
//import static com.tofpu.toolbar.helper.ToolHelper.getItemOrThrow;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class ToolbarTest extends FullTestBoostrap {
//    @Test
//    void register_regular_toolbar_with_a_tool_containing_unspecified_index_test() {
//        int itemAmount = 1;
//
//        Toolbar toolbar = new ToolbarImpl("bar", tools(
//                tool("tool", new ItemStack(Material.DIAMOND, itemAmount))
//        ));
//
//        assertEquals(1, toolbar.size());
//
//        ItemStack itemStack = getItemOrThrow(toolbar, 0);
//        assertNotNull(itemStack);
//
//        assertEquals(Material.DIAMOND, itemStack.getType());
//        assertEquals(itemAmount, itemStack.getAmount());
////        new Tool("", item(Material.DIAMOND), (ToolAction.PlayerEntityInteract) (owner, event) -> {
////
////        });
////
////        new Tool("2", item(Material.DIAMOND), ToolAction.of(PlayerInteractEvent.class, (owner, event) -> {
////
////        }));
//
//        new Tool("3", item(Material.DIAMOND), (ToolActionTypes.PlayerEntityInteract) (owner, event) -> {
//
//        });
//    }
//
//    @Test
//    void register_a_tool_containing_an_index_test() {
//        int itemIndex = 5;
//        int itemAmount = 1;
//
//        SimpleToolbar toolbar = new SimpleToolbar("bar", withToolSlot(
//                ItemSlot.atIndex(itemIndex),
//                tool("tool", new ItemStack(Material.DIAMOND, itemAmount))
//        ));
//
//        Map<ItemSlot, ItemStack> itemStacks = toolbar.getItemsWithSlots();
//        assertEquals(1, itemStacks.size());
//
//        ItemStack itemStack = itemStacks.get(ItemSlot.atIndex(itemIndex));
//        assertNotNull(itemStack);
//
//        assertEquals(Material.DIAMOND, itemStack.getType());
//        assertEquals(itemAmount, itemStack.getAmount());
//    }
//
//    @Test
//    void item_nbt_after_added_in_constructor() {
//        SimpleToolbar toolbar = new SimpleToolbar("bar", singleTool(tool("first_tool", Material.DIAMOND)));
//        toolbar.getTools().forEach(tool -> {
//            ItemNBTHandler nbtHandler = api.itemNBTHandler(tool.getItem());
//            assertEquals(toolbar.getIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOLBAR_NBT_KEY));
//            assertEquals(tool.getItemIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOL_NBT_KEY));
//        });
//    }
//
//    @Test
//    void item_nbt_after_added_after_construction() {
//        SimpleToolbar toolbar = new SimpleToolbar("bar", tools());
//        toolbar.addItem(tool("first_tool", Material.DIAMOND));
//
//        toolbar.getTools().forEach(tool -> {
//            ItemNBTHandler nbtHandler = api.itemNBTHandler(tool.getItem());
//            assertEquals(toolbar.getIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOLBAR_NBT_KEY));
//            assertEquals(tool.getItemIdentifier(), nbtHandler.getString(ToolNBTUtil.TOOL_NBT_KEY));
//        });
//    }
//}
