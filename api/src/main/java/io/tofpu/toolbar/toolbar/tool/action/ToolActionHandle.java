package io.tofpu.toolbar.toolbar.tool.action;

import io.tofpu.toolbar.toolbar.Toolbar;
import org.bukkit.event.Event;

@FunctionalInterface
public interface ToolActionHandle<E extends Event> {
    void handle(final Toolbar owner, final E event);
}
