package io.tofpu.toolbar.helper;

import io.tofpu.toolbar.toolbar.ItemSlot;
import io.tofpu.toolbar.toolbar.ToolWithSlot;
import io.tofpu.toolbar.toolbar.tool.Tool;
import io.tofpu.toolbar.toolbar.tool.action.ToolAction;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ObjectCreationHelper {
    public static ToolWithSlot<Tool> withToolSlot(ItemSlot slot, Tool tool) {
        return new ToolWithSlot<>(tool, slot);
    }

    public static ToolWithSlot<Tool> withUndefinedSlot(Tool tool) {
        return withToolSlot(ItemSlot.undefined(), tool);
    }

    public static Tool tool(String id, ItemStack itemStack) {
        return new Tool(id, itemStack);
    }

    public static Tool tool(String id, ItemStack itemStack, ToolAction action) {
        return new Tool(id, itemStack, action);
    }

    public static Tool tool(String id, Material material, ToolAction action) {
        return new Tool(id, item(material), action);
    }

    public static Tool tool(String id, Material material) {
        return tool(id, item(material));
    }

    public static Tool[] tools(Tool... tools) {
        return Arrays.stream(tools).toArray(Tool[]::new);
    }

    public static Tool[] singleTool(Tool tool) {
        return new Tool[]{tool};
    }

    public static ItemStack item(Material material) {
        return new ItemStack(material);
    }
}
