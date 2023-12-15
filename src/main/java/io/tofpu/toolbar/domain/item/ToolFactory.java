package io.tofpu.toolbar.domain.item;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.tofpu.toolbar.domain.Toolbar;
import io.tofpu.toolbar.domain.item.Tool;
import io.tofpu.toolbar.domain.item.AbstractToolAction;
import org.bukkit.inventory.ItemStack;

public final class ToolFactory {
    public Tool create(final Toolbar owner, final String itemIdentifier
            , final ItemStack item, final AbstractToolAction itemAction) {
        return create(owner, itemIdentifier, item, -1, itemAction);
    }

    public Tool create(final Toolbar owner, final String itemIdentifier
            , final ItemStack item, final int index,
                       final AbstractToolAction itemAction) {
        final NBTItem nbtItem = new NBTItem(item, true);

        // adding the toolbar nbt tag to the item
        nbtItem.setString("toolbar_identifier", owner.getIdentifier());

        // adding the tool nbt tag to the item
        nbtItem.setString("tool_identifier", itemIdentifier);

        return new Tool(owner, itemIdentifier, item, index, itemAction);
    }
}
