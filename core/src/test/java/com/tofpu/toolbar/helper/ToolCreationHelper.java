package com.tofpu.toolbar.helper;

import com.tofpu.toolbar.api.toolbar.ItemSlot;
import com.tofpu.toolbar.api.toolbar.tool.Tool;
import com.tofpu.toolbar.api.toolbar.tool.action.ToolAction;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ToolCreationHelper {
    public static Tool withTool(ItemSlot slot, Tool tool) {
        return tool.at(slot);
    }

    public static Tool withUndefinedSlot(Tool tool) {
        return withTool(ItemSlot.undefined(), tool);
    }

    public static Tool tool(String id, ItemStack itemStack) {
        return Tool.of(id, itemStack, ToolAction.nothing(), ItemSlot.undefined());
    }

    public static Tool tool(String id, ItemStack itemStack, ToolAction action) {
        return Tool.of(id, itemStack, action, ItemSlot.undefined());
    }

    public static Tool tool(String id, Material material, ToolAction action) {
        return Tool.of(id, item(material), action, ItemSlot.undefined());
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
