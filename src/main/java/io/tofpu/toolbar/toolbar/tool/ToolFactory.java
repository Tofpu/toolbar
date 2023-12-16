package io.tofpu.toolbar.toolbar.tool;

import org.bukkit.inventory.ItemStack;

public final class ToolFactory {
    private ToolFactory() {
    }

    public static Tool create(final String itemIdentifier,
                              final ItemStack item,
                              final ToolAction itemAction) {
        return create(itemIdentifier, item, ItemSlot.undefined(), itemAction);
    }

    public static Tool create(final String itemIdentifier,
                              final ItemStack item,
                              final ItemSlot index, final
                              ToolAction itemAction) {
        return new Tool(itemIdentifier, item, index, itemAction);
    }
}
