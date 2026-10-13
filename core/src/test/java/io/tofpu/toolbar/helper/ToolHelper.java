package io.tofpu.toolbar.helper;

import io.tofpu.toolbar.toolbar.ItemSlot;
import io.tofpu.toolbar.toolbar.Toolbar;
import io.tofpu.toolbar.toolbar.tool.Tool;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ToolHelper {
    public static @NotNull ItemStack getItemOrThrow(Toolbar toolbar, int index) {
        return toolbar.itemAt(ItemSlot.atIndex(index))
                .orElseThrow(() -> new RuntimeException("No item at index " + index));
    }

    public static @NotNull ItemStack getItemOrThrow(Toolbar toolbar, String id) {
        return toolbar.item(id)
                .orElseThrow(() -> new RuntimeException("No item with id " + id));
    }

    public static @NotNull Tool getToolOrThrow(Toolbar toolbar, String id) {
        return toolbar.tool(id)
                .orElseThrow(() -> new RuntimeException("No item with id " + id));
    }
}
