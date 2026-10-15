package com.tofpu.toolbar.api.listener;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public interface ToolEventListener<E extends Event> {
    void onTrigger(E event, ToolActivator<Event> toolActivator);
    Class<E> type();

    @FunctionalInterface
    interface ToolActivator<E extends Event> {
        void activate(E event, ItemStack item);
    }
}
