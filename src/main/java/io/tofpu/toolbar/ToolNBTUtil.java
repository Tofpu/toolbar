package io.tofpu.toolbar;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.tofpu.toolbar.toolbar.Toolbar;
import io.tofpu.toolbar.toolbar.item.Tool;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ToolNBTUtil {
    public static final String TOOLBAR_NBT_KEY = "toolbar_identifier";
    public static final String TOOL_NBT_KEY = "tool_identifier";

    public static void tag(Toolbar owner, Tool tool) {
        final NBTItem nbtItem = new NBTItem(tool.getItem(), true);

        // adding the toolbar nbt tag to the item
        nbtItem.setString(TOOLBAR_NBT_KEY, owner.getIdentifier());

        // adding the tool nbt tag to the item
        nbtItem.setString(TOOL_NBT_KEY, tool.getItemIdentifier());
    }

    public static String getToolIdBy(final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        return new NBTItem(itemStack).getString(TOOL_NBT_KEY);
    }

    public static String getToolbarIdBy(final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        return new NBTItem(itemStack).getString(TOOLBAR_NBT_KEY);
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
