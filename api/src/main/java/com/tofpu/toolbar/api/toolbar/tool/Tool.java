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

    static Builder newBuilder(String id) {
        return new Builder(id);
    }

    boolean trigger(Toolbar owner, Event event);

    String id();

    ItemStack item();

    ToolAction<?> action();

    ItemSlot slot();

    Tool at(ItemSlot newSlot);

    class Builder {
        private final String id;
        private ToolAction<?> action = ToolAction.nothing();
        private ItemSlot itemSlot = ItemSlot.undefined();

        private Builder(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }

        public ToolAction<?> action() {
            return action;
        }

        public Builder setAction(ToolAction<?> action) {
            this.action = action;
            return this;
        }

        public ItemSlot itemSlot() {
            return itemSlot;
        }

        public Builder setItemSlot(ItemSlot itemSlot) {
            this.itemSlot = itemSlot;
            return this;
        }

        public Tool build(ItemStack itemStack) {
            return Tool.of(
                    id,
                    itemStack,
                    action,
                    itemSlot
            );
        }
    }
}
