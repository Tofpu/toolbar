package io.tofpu.toolbar.toolbar.item;

import org.bukkit.inventory.ItemStack;

public final class ToolFactory {
    private ToolFactory() {}

    public static Tool create(final String itemIdentifier
            , final ItemStack item, final ToolAction itemAction) {
        return create(itemIdentifier, item, -1, itemAction);
    }

    public static Tool create(final String itemIdentifier
            , final ItemStack item, final int index,
                       final ToolAction itemAction) {
        return new Tool(itemIdentifier, item, index, itemAction);
    }
}
