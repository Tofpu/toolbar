package io.github.tofpu;

import io.tofpu.toolbar.nbt.ItemNBTHandler;
import io.tofpu.toolbar.ToolbarAPI;
import io.tofpu.toolbar.toolbar.tool.Tool;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ToolNBTUtil {
    public static final String TOOLBAR_NBT_KEY = "toolbar_identifier";
    public static final String TOOL_NBT_KEY = "tool_identifier";

    public static void tag(String ownerId, Tool tool) {
        final ItemNBTHandler itemNBTHandler = itemNBTHandler(tool.item());

        // adding the toolbar nbt tag to the item
        itemNBTHandler.setString(TOOLBAR_NBT_KEY, ownerId);

        // adding the tool nbt tag to the item
        itemNBTHandler.setString(TOOL_NBT_KEY, tool.id());
    }

    public static String getToolIdBy(final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        return itemNBTHandler(itemStack).getString(TOOL_NBT_KEY);
    }

    public static String getToolbarIdBy(final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        return itemNBTHandler(itemStack).getString(TOOLBAR_NBT_KEY);
    }

    private static ItemNBTHandler itemNBTHandler(ItemStack itemStack) {
        return ToolbarAPI.getInstance().itemNBTHandler(itemStack);
    }

    public static boolean isTool(final ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return false;
        }
        String id = getToolbarIdBy(itemStack);
        return id != null && !id.isEmpty();
    }

    public static boolean isNotTool(final ItemStack itemStack) {
        return !isTool(itemStack);
    }
}
