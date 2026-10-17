package com.tofpu.toolbar.api.toolbar.tool;

import com.tofpu.toolbar.api.toolbar.ItemSlot;
import com.tofpu.toolbar.api.toolbar.Toolbar;
import com.tofpu.toolbar.api.toolbar.tool.action.ToolAction;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public interface Tool {

    static Tool of(final String id, final ItemStack item, final ToolAction<? extends Event> action, final ItemSlot slot) {
        return new ToolImpl(id, item, action, slot);
    }

    boolean trigger(Toolbar owner, Event event);

    String id();

    ItemStack item();

    ToolAction<?> action();

    ItemSlot slot();

    Tool at(ItemSlot newSlot);
}
