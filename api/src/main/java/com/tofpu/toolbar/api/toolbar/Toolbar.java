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

    Optional<Tool> tool(String identifier);

    Optional<Tool> toolAt(ItemSlot slot);

    Collection<Tool> tools();

    default Optional<ItemStack> item(String id) {
        return tool(id)
                .map(Tool::item);
    }

    default Optional<ItemStack> itemAt(ItemSlot slot) {
        return toolAt(slot).map(Tool::item);
    }

    default Collection<ItemStack> items() {
        return tools().stream()
                .map(Tool::item)
                .collect(Collectors.toList());
    }

    default Map<ItemSlot, ItemStack> itemsWithSlots() {
        Map<ItemSlot, ItemStack> itemStackMap = new HashMap<>();
        for (Tool toolWithSlot : tools()) {
            ItemSlot itemSlot = toolWithSlot.slot();
            itemStackMap.put(itemSlot, toolWithSlot.item());
        }
        return itemStackMap;
    }

    default int size() {
        return tools().size();
    }
}
