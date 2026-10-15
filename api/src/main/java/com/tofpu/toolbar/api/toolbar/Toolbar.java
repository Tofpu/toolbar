package com.tofpu.toolbar.api.toolbar;

import com.tofpu.toolbar.api.toolbar.tool.Tool;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Toolbar {
    String id();

    Optional<ToolWithSlot> toolWithSlot(String identifier);

    Optional<ToolWithSlot> toolWithSlotAt(ItemSlot slot);

    Collection<ToolWithSlot> toolsWithSlots();

    default Optional<Tool> tool(String identifier) {
        return toolWithSlot(identifier)
                .map(ToolWithSlot::tool);
    }

    default Optional<ItemStack> item(String id) {
        return tool(id)
                .map(Tool::item);
    }

    default Optional<Tool> toolAt(ItemSlot slot) {
        return toolWithSlotAt(slot).map(ToolWithSlot::tool);
    }

    default Optional<ItemStack> itemAt(ItemSlot slot) {
        return toolAt(slot).map(Tool::item);
    }

    default Collection<Tool> tools() {
        return toolsWithSlots().stream()
                .map(ToolWithSlot::tool)
                .collect(Collectors.toList());
    }

    default Collection<ItemStack> items() {
        return tools().stream()
                .map(Tool::item)
                .collect(Collectors.toList());
    }

    default Map<ItemSlot, ItemStack> itemsWithSlots() {
        Map<ItemSlot, ItemStack> itemStackMap = new HashMap<>();
        for (ToolWithSlot toolWithSlot : toolsWithSlots()) {
            ItemSlot itemSlot = toolWithSlot.slot();
            itemStackMap.put(itemSlot, toolWithSlot.tool().item());
        }
        return itemStackMap;
    }

    default int size() {
        return toolsWithSlots().size();
    }
}
