package io.tofpu.toolbar.toolbar.tool.action;

import io.tofpu.toolbar.toolbar.GenericToolbar;
import org.bukkit.event.Event;

@FunctionalInterface
public interface ToolActionHandle<E extends Event> {
    void handle(final GenericToolbar<?> owner, final E event);
}
