package io.tofpu.toolbar.helper;

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
    public static Tool tool(String id, ItemStack itemStack, int itemIndex) {
        return new Tool(id, itemStack, itemIndex);
    }

    public static Tool tool(String id, ItemStack itemStack, int itemIndex, ToolAction action) {
        return new Tool(id, itemStack, itemIndex, action);
    }

    public static Tool tool(String id, ItemStack itemStack) {
        return tool(id, itemStack, -1);
    }

    public static Tool tool(String id, Material material, ToolAction action) {
        return tool(id, item(material), -1, action);
    }

    public static Tool tool(String id, Material material) {
        return tool(id, item(material), -1);
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
