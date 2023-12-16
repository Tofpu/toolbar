package io.tofpu.toolbar.helper;

import io.tofpu.toolbar.toolbar.tool.ItemSlot;
import io.tofpu.toolbar.toolbar.tool.Tool;
import io.tofpu.toolbar.toolbar.tool.ToolAction;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class ObjectCreationHelper {
    public static Tool tool(String id, ItemStack itemStack, ItemSlot slot) {
        return new Tool(id, itemStack, slot);
    }

    public static Tool tool(String id, ItemStack itemStack, ItemSlot itemSlot, ToolAction action) {
        return new Tool(id, itemStack, itemSlot, action);
    }

    public static Tool tool(String id, ItemStack itemStack) {
        return tool(id, itemStack, ItemSlot.undefined());
    }

    public static Tool tool(String id, Material material, ToolAction action) {
        return tool(id, item(material), ItemSlot.undefined(), action);
    }

    public static Tool tool(String id, Material material) {
        return tool(id, item(material), ItemSlot.undefined());
    }

    public static Collection<Tool> tools(Tool... tools) {
        return Arrays.stream(tools).collect(Collectors.toCollection(LinkedList::new));
    }

    public static Collection<Tool> singleTool(Tool tool) {
        return Collections.singletonList(tool);
    }

    public static ItemStack item(Material material) {
        return new ItemStack(material);
    }
}
